
package com.abcbank.gl;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndOfDayProcessor {
    private Map<String, List<Position>> positions;
    private List<Transaction> transactions = new ArrayList<Transaction>();
	Logger logger = LoggerFactory.getLogger(EndOfDayProcessor.class);
    private Map<String, List<EndOfDayPosition>> endOfDayPositions = new LinkedHashMap<String, List<EndOfDayPosition>>();

    public void process(String positionPath, String transactionPath, String endOfDayPositionPath) throws IOException, ParseException, org.json.simple.parser.ParseException {
    	logger.info("This is how you configure Log4J with SLF4J");
        loadPositions(Paths.get(positionPath));
        logger.info(positions.toString());
        loadTransactions(transactionPath);
        logger.info("Transactions"+transactions);
        loadEndOfDayPositions();
        logger.info(endOfDayPositions.toString());
        transactions.forEach( t -> endOfDayPositions.get(t.getInstrumentSymbol()).forEach(eodp -> eodp.applyTransaction(t)));        
        logger.info(endOfDayPositions.toString());
        PrintWriter out = new PrintWriter(endOfDayPositionPath);
        out.println("Instrument,Account,AccountType,Quantity,Delta");
        endOfDayPositions.values().stream().flatMap(List::stream)
                .map(EndOfDayPosition::toCSV)
                //.peek(System.out::println)
                .forEach(out::println);
        out.close();
    }
    private static EndOfDayPosition getEndOfDayPosition(Position p) {
        EndOfDayPosition eodp = new EndOfDayPosition();
        eodp.setPosition(p);
        return eodp;
    }
    private void loadPositions(Path positionPath) throws IOException {
        positions = Files.lines(positionPath).skip(1).map(Position::parsePosition)
            .collect(Collectors.groupingBy(Position::getInstrumentSymbol,LinkedHashMap::new, Collectors.toList()));
    }
    private void loadEndOfDayPositions() {
    	//logger.info(positions.values().stream().flatMap(List::stream).collect(Collectors.toList()));
    	//logger.info(positions.values().stream().flatMap(List::stream).map(EndOfDayProcessor::getEndOfDayPosition).collect(Collectors.toList()));
        endOfDayPositions = positions.values().stream().flatMap(List::stream).map(EndOfDayProcessor::getEndOfDayPosition)
            .collect(Collectors.groupingBy(EndOfDayPosition::getInstrumentSymbol,LinkedHashMap::new, Collectors.toList()));
    }
    private void loadTransactions(String transactionPath) throws IOException, ParseException, org.json.simple.parser.ParseException {
        Object obj = new JSONParser().parse(new FileReader(transactionPath));
        JSONArray ja = (JSONArray) obj;
		Iterator<?> transIterator = ja.iterator();
        while (transIterator.hasNext()) {
            JSONObject jsonObj = (JSONObject)transIterator.next();
            Transaction t = new Transaction();
            t.setId((int)(long)jsonObj.get("TransactionId"));
            t.setInstrumentSymbol((String)jsonObj.get("Instrument"));
            t.setType(Transaction.Type.valueOf((String)jsonObj.get("TransactionType")));
            t.setQuantity((long)jsonObj.get("TransactionQuantity"));
            this.transactions.add(t);
        }
    }
    public static void main(String[] args) throws Exception {
        EndOfDayProcessor processor = new EndOfDayProcessor();
        String positionPath = "D://gls//Input_StartOfDay_Positions.txt";
        String transactionPath = "D://gls//Input_Transactions.txt";
        String endOfDayPositionPath = "D://gls//out.txt";
        /*String positionPath = args[0];
        String transactionPath = args[1];
        String endOfDayPositionPath = args[2];*/
        processor.process(positionPath, transactionPath, endOfDayPositionPath);
    }
}


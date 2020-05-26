# PriteshMistry_Java


Please copy below files on the respective location and change the file path in App.java on local directory.

String positionPath = "D://gls//Input_StartOfDay_Positions.txt";
String transactionPath = "D://gls//Input_Transactions.txt";

String result = strList.stream()
			      .map(n -> String.valueOf(n))
			      .collect(Collectors.joining("\",\"", "{\"", "\"}"));
			      
			      


SELECT EMP_ID,EMP_NAME,VERSION_START_DATE,VERSION_END_DATE , 'UPDATE' STATUS FROM EMP
WHERE VERSION_END_DATE IN  (
SELECT VERSION_START_DATE FROM EMP WHERE VERSION_START_DATE >  SYSDATE -1 )
UNION
SELECT EMP_ID,EMP_NAME,VERSION_START_DATE,VERSION_END_DATE, 'INSERT' STATUS FROM EMP WHERE VERSION_START_DATE >  SYSDATE -1
		

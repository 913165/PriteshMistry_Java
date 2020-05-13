# PriteshMistry_Java


Please copy below files on the respective location and change the file path in App.java on local directory.

String positionPath = "D://gls//Input_StartOfDay_Positions.txt";
String transactionPath = "D://gls//Input_Transactions.txt";

String result = strList.stream()
			      .map(n -> String.valueOf(n))
			      .collect(Collectors.joining("\",\"", "{\"", "\"}"));
		

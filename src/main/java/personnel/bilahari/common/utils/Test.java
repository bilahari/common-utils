package personnel.bilahari.common.utils;

import java.io.File;
import java.util.List;


	public class Test implements Runnable{

		public static void main(String[] args){
			Test ctj = new Test();
			ctj.run();
		}
		
		public void run() {
			File folder = new File("E:\\PARAMS");
			File destFolder = new File("E:\\PARAMS\\MOVED");
			while(true){
				System.out.println("here");
				for (final File file : folder.listFiles()) {
					if(file.isDirectory()) continue;
					file.getName();
					//List<String> paramsList = Utils.readFileLineByLine(file.getAbsolutePath());
					//String param = paramsList.get(0);
				//	String prismResp = HTTPHelper.getResponseFromURL("http://localhost:8080/cwb/test2.jsp", null, 0, 0);
					String prismResp = "successs";
					
					if(prismResp.trim()
							.equalsIgnoreCase("success")){
						file.renameTo(new File(destFolder+"\\"+file.getName()));
					}else{
						file.renameTo(new File(destFolder+"\\"+file.getName()));
					}
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	
	/*
	//@Override
		public static void run() {
			File folder = new File("E:\\PARAMS");
			File destFolder = new File("E:\\PARAMS\\MOVED");
			
			for (final File file : folder.listFiles()) {
				file.getName();
				if(file.isDirectory()) continue;
				List<String> paramsList = FileUtils.readFileLineByLine(file.getAbsolutePath());
				System.out.println(paramsList.get(0));
				file.renameTo(new File(destFolder+"\\"+file.getName()));
			}
		}
		*/
		
		
		
}

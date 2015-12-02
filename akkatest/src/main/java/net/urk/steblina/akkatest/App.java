package net.urk.steblina.akkatest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class App {
	private static File inFile;
	private static File outFile;
	
	public static void main(String[] args) {
		if(args.length!=0&&args[0].equals("-auto")){
			inFile = generateAuto();
			outFile = new File("outtest.txt");
		}else if(args.length==2){
			inFile = new File(args[0]);
			outFile = new File(args[1]);
		}else{
			System.out.println("Try to start:");
			System.out.println("akkatest.jar -auto");
			System.out.println("or");
			System.out.println("akkatest.jar /path/to/infile /path/to/outfile");
			System.exit(0);
		}
		System.out.println("In file: "+inFile.getAbsolutePath());
		ActorSystem system = ActorSystem.create("akkatest");
		ActorRef kernel = system.actorOf(Props.create(Kernel.class), "kernel");
		kernel.tell(outFile, kernel);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(inFile));
			kernel.tell(br, kernel);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static File generateAuto(){
		inFile = new File("intest.txt");
		final Random random = new Random();
		try {
			PrintWriter writer = new PrintWriter(inFile);
			for(int i=0;i<100000;i++){
				writer.println((random.nextInt(1000)+1)+";"+(random.nextInt(20000)+1));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return inFile;
	}
}

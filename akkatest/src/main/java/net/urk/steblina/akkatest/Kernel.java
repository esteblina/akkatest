package net.urk.steblina.akkatest;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.SmallestMailboxPool;

public class Kernel extends UntypedActor {
	
	private ActorRef router = getContext().actorOf(new SmallestMailboxPool(100).props(Props.create(LineProcessing.class)), "processing");
	private Map<Integer,Long> ids = new HashMap<Integer,Long>();
	private File outFile;
	private int count=0;
	private int lines=0;

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof File) {	
			outFile=(File)arg0;
		}

		if (arg0 instanceof BufferedReader) {	
			BufferedReader br = (BufferedReader)arg0;
			
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				lines++;
				router.tell(currentLine, getSelf());	
			}
			br.close();

		}
		if (arg0 instanceof ID){
			count++;
			ID id =(ID)arg0;
			
			Long op=ids.putIfAbsent(id.getId(), id.getOperations());
			if(op!=null){
				ids.put(id.getId(), id.getOperations()+op);
			}
			if(count==lines){
				PrintWriter writer = new PrintWriter(outFile);
				
				for(Integer i : ids.keySet()){
					writer.println(i+";"+ids.get(i));
				}
				writer.close();
			System.out.println("Out file: "+outFile.getAbsolutePath());
				getContext().system().shutdown();
			}
		}
		unhandled(arg0);	

	}

}

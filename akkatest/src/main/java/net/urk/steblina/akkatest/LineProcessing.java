package net.urk.steblina.akkatest;


import akka.actor.UntypedActor;

public class LineProcessing extends UntypedActor {

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof String) {
			String[] line=((String)arg0).split(";");
			
			getSender().tell(new ID(Integer.parseInt(line[0]),Long.parseLong(line[1])), getSelf());
		}
		unhandled(arg0);

	}

}

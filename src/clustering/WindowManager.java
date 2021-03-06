package clustering;

import java.util.Queue;

/**
 * 
 * @author colonelmo
 * Handling graph updates
 */
public class WindowManager {
	private int currentTimestamp ; 
	private CountBasedSlidingWindow window ;
	private Graph gr ;
	static int nextEdgeId ;
	
	static{
		nextEdgeId = 1 ;
	}
	
	public WindowManager(Graph gr){
		this.gr = gr ;
		currentTimestamp = 1 ;
		window = new CountBasedSlidingWindow(this , help.Constants.Parameters.WINDOW_SIZE);
	}
	
	public int getNow(){
		return currentTimestamp ;
	}
	
	private Edge createEdge(Pair p){
		return new Edge(p.a , p.b , currentTimestamp, nextEdgeId ++) ;
	}
	
	private void advanceTimestamp(){
		currentTimestamp++ ;
	}
	
	public void update(AccumulatingGraph g){
//		System.out.println("current time : " + currentTimestamp);
		int tm = 0 ;
		for(Pair e : g.getInsertions()){
			if(tm != 0 && tm % 1000 == 0)
				System.out.println(tm);
			window.handleNewEdge(createEdge(e));
			tm ++ ;
		}
		// No deletion support yet !
		advanceTimestamp();
	}
	
	public GraphManager getGraphManager(){
		return gr.getGraphManager() ;
	}
	
}

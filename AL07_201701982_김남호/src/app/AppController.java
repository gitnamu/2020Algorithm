package app;

import graph.AdjacencyGraph;
import graph.WeightedDirectedAdjacencyListGraph;
import graph.WeightedEdge;
import shortestPaths.ShortestPaths;
import list.LinkedStack;
import list.Iterator;

public class AppController {
	// Private instance variables
	private AdjacencyGraph<WeightedEdge>	_graph ;
	private ShortestPaths<WeightedEdge>		_shortestPaths;
	
	// Getters & Setters
	private AdjacencyGraph<WeightedEdge> graph() {
		return this._graph;
	}
	private void setGraph (AdjacencyGraph<WeightedEdge> newGraph) {
		this._graph = newGraph;
	}
	
	public ShortestPaths<WeightedEdge> shortestPaths(){
		return this._shortestPaths;
	}
	public void setShortestPaths(ShortestPaths<WeightedEdge> newShortestPaths) {
		this._shortestPaths = newShortestPaths;
	}
	
	// Constructor
	public AppController() {
		this.setGraph (null);
		this.setShortestPaths(new ShortestPaths<WeightedEdge>());
	}
	
	//edge를 입력하도록 하는 메소드
	private WeightedEdge inputEdge() {
		do {
			//vertex 두개를 입력받는다
			AppView.outputLine("- 입력할 edge의 두 vertex와 cost를 차례로 입력해야 합니다:");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			int cost = AppView.inputCost();
			//입력받은 vertex가 존재하는 vertex인지 확인한다
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex) && cost>0) {
				//만약 두 vertex가 동일한 vertex이면 오류 메세지를 출력한다
				if(tailVertex == headVertex) {
					AppView.outputLine("[오류] 두 vertex 번호가 동일합니다.");
				}
				//정상적인 vertex쌍이면 해당 vertex들을 tail, head로 하는 edge를 생성한다
				else {
					return (new WeightedEdge(tailVertex, headVertex, cost));
				}
			}
			//tail vertex 또는 head vertex가 존재하지 않는 vertex이면 오류 메세지를 출력한다
			else {
				if(! this.graph().vertexDoesExist(tailVertex)) {
					AppView.outputLine("[오류] 존재하지 않는 tail vertex 입니다: "+tailVertex);
				}
				if(! this.graph().vertexDoesExist(headVertex)) {
					AppView.outputLine("[오류] 존재하지 않는 head vertex 입니다: "+headVertex);
				}
				if(cost<0) {
					AppView.outputLine("[오류] edge의 비율은 양수이어야 합니다: "+cost);
				}
			}
		}while(true);	//정상적인 vertex를 입력 받을 때까지 무한반복한다
	}
	
	//vertex수, edge수를 입력받고 그래프를 생성한다
	private void inputAndMakeGraph () {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다:");
		//vertex수를 입력받는다
		int numberOfVertices = this.inputNumberOfVertices();
		//vertex 수 크기의 graph를 생성한다
		this.setGraph(new WeightedDirectedAdjacencyListGraph<WeightedEdge>(numberOfVertices));
		//edge 수를 입력받는다
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		
		int edgeCount = 0;
		//edge를 입력받는다
		while(edgeCount < numberOfEdges) {
			WeightedEdge edge = this.inputEdge();
			//입력된 edge가 이미 존재하는 edge이면 오류 메세지를 출력한다
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(오류) 입력된 edge ("+ edge.tailVertex()+ "," +  edge.headVertex() + 
						", (" + edge.weight() + "))는 그래프에 이미 존재합니다.");
			}
			else {
				//오류가 발생하지 않았으면 생성된 edge수를 +1하고, 정상적으로 삽입되었음을 알리는 메세지를 출력한다
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!새로운 edge (" + edge.tailVertex() + "," + edge.headVertex() +
						", (" + edge.weight() + ")) 가 그래프에 삽입되었습니다.");
			}
		}
	}
	//입력된 그래프를 출력하는 그래프
	private void showGraph () {
		AppView.outputLine("");
		AppView.outputLine("> 입력된 그래프는 다음과 같습니다:");
		//모든 vertex들을 출력한다
		for(int tailVertex = 0;tailVertex < this.graph().numberOfVertices();tailVertex++) {
			AppView.output("["+tailVertex+"] ->");
			Iterator<WeightedEdge> neighborIterator = 
					this.graph().neighborIteratorOf(tailVertex);
			while(neighborIterator.hasNext()) {
				WeightedEdge nextEdge = neighborIterator.next();
				AppView.output(" " + nextEdge.headVertex());
				AppView.output("(" + nextEdge.weight() + ")");
			}
			AppView.outputLine("");
		}
	}
	
	//vertex 수를 입력하도록 하는 메소드
	private int inputNumberOfVertices () {
		//올바른 수가 입력 될 때 까지 반복한다
		while (true) {
			AppView.output ("? Vertex 수를 입력하시오: ") ;
			//입력된 수가 int형으로 형변환이 불가능하고 1보다 작으면 오류 메세지를 출력한다
			try {
				int numberOfVertices = AppView.inputInt() ;
				if ( numberOfVertices < 1 ) {
					AppView.output ("[오류] Vertex 수는 0 보다 커야 합니다.");
				}
				else {
					return numberOfVertices ;	
				}
			}
			catch (NumberFormatException e) {
				AppView.outputLine ("[오류] 올바른 숫자가 입력되지 않았습니다.");
			}
		}
	}

	//edge의 수를 입력받는다
	private int inputNumberOfEdges () {
		//올바른 수가 입력될 때 까지 반복한다
		while (true) {
			AppView.output ("? Edge 수를 입력하시오: ") ;
			//입력받은 수가 int형이 아니고 0보다 작은경우 오류 메세지를 출력한다
			try {
				int numberOfEdges = AppView.inputInt() ;
				if ( numberOfEdges < 0 ) {
					AppView.output ("[오류] Edge 수는 0 보다 크거나 같아야 합니다.") ;
				}
				else {
					return numberOfEdges ;
				}
			}
			catch (NumberFormatException e) {
				AppView.outputLine ("[오류] 올바른 숫자가 입력되지 않았습니다.") ;
			}
		}
	}
	
	private int inputSourceVertex() {
		int sourceVertex = AppView.inputSourceVertex();
		while(! this.graph().vertexDoesExist(sourceVertex)) {
			AppView.outputLine("[오류] 입력된 출발 vertex는 존재하지 않습니다: " + sourceVertex);
			sourceVertex = AppView.inputSourceVertex();
		}
		return sourceVertex;
	}
	
	private void solveAndShowShortestPaths() {
		AppView.outputLine("");
		AppView.outputLine("> 주어진 그래프에서 최단 경로를 찾습니다:");
		if(this.graph().numberOfVertices() <= 1) {
			AppView.outputLine("[오류] vertex 수(" + this.graph().numberOfVertices() +
						") 가 너무 적어서, 최단경로 찾기를 하지 않습니다. 2개 이상이어야 합니다.");
		}
		else {
			AppView.outputLine("> 출발점을 입력해야 합니다:");
			int sourceVertex = this.inputSourceVertex();
			if(this.shortestPaths().solve(this.graph(), sourceVertex)) {
				AppView.outputLine("");
				AppView.outputLine("> 최단 경로별 비용과 경로는 다음과 같습니다:");
				AppView.outputLine("출발점=" + sourceVertex + ":");
				for(int destination = 0;destination < this.graph().numberOfVertices();destination++) {
					if(destination != sourceVertex) {
						AppView.output("  [목적점=" + destination + "] ");
						AppView.output("최소비용=" + this.shortestPaths().minCostOfPathToDestination(destination) + ", ");
						AppView.output("경로:");
						LinkedStack<Integer> pathToDestination = this.shortestPaths().pathToDestination(destination);
						LinkedStack<Integer>.IteratorForLinkedStack iterator = pathToDestination.iterator();
						while(iterator.hasNext()) {
							AppView.output(" -> " + iterator.next());
						}
						AppView.outputLine("");
					}
				}
			}
			else {
				AppView.outputLine("[오류] 최단경로 찾기를 실패하였습니다.");
			}
		}
	}
	
	public void run() {
		AppView.outputLine("<<< 최단경로 찾기 프로그램을 시작합니다 >>>");
		//vertex 수, edge수를 입력받고 그래프를 생성한다
		this.inputAndMakeGraph();
		//생성된 그래프를 출력한다
		this.showGraph();
		
		this.solveAndShowShortestPaths();
		AppView.outputLine("");
		AppView.outputLine("<<< 최단경로 찾기 프로그램을 종료합니다 >>>");
	}

}
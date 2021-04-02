package app;

import graph.AdjacencyGraph;
import graph.DirectedAdjacencyListGraph;
import graph.Edge;
import list.Iterator;
import list.List;
import topologicalSort.TopologicalSort;

public class AppController {
	// Private instance variables
	private AdjacencyGraph<Edge>	_graph ;
	private TopologicalSort<Edge>	_topologicalsort;
	
	// Getters & Setters
	private AdjacencyGraph<Edge> graph() {
		return this._graph;
	}
	private void setGraph (AdjacencyGraph<Edge> newGraph) {
		this._graph = newGraph;
	}
	
	private TopologicalSort<Edge> topologicalSort(){
		return this._topologicalsort;
	}
	private void setTopological(TopologicalSort<Edge> newTopologicalSort) {
		this._topologicalsort = newTopologicalSort;
	}
	
	// Constructor
	public AppController() {
		this.setGraph (null);
		this.setTopological(new TopologicalSort<Edge>());
	}
	
	//edge를 입력하도록 하는 메소드
	private Edge inputEdge() {
		do {
			//vertex 두개를 입력받는다
			AppView.outputLine("- 입력할 edge의 두 vertex를 차례로 입력해야 합니다:");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			//입력받은 vertex가 존재하는 vertex인지 확인한다
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {
				//만약 두 vertex가 동일한 vertex이면 오류 메세지를 출력한다
				if(tailVertex == headVertex) {
					AppView.outputLine("[오류] 두 vertex 번호가 동일합니다.");
				}
				//정상적인 vertex쌍이면 해당 vertex들을 tail, head로 하는 edge를 생성한다
				else {
					return (new Edge(tailVertex, headVertex));
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
			}
		}while(true);	//정상적인 vertex를 입력 받을 때까지 무한반복한다
	}
	
	//vertex수, edge수를 입력받고 그래프를 생성한다
	private void inputAndMakeGraph () {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다:");
		//vertex수를 입력받는다
		int numberOfVertices = this.inputNumberOfVertices();
		//vertex 수 크기의 graph를 생성한다
		this.setGraph(new DirectedAdjacencyListGraph<Edge>(numberOfVertices));
		//edge 수를 입력받는다
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		
		int edgeCount = 0;
		//edge를 입력받는다
		while(edgeCount < numberOfEdges) {
			Edge edge = this.inputEdge();
			//입력된 edge가 이미 존재하는 edge이면 오류 메세지를 출력한다
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(오류) 입력된 edge ("+ 
			edge.tailVertex()+ "," +  edge.headVertex() + ") 는 그래프에 이미 존재합니다.");
			}
			else {
				//오류가 발생하지 않았으면 생성된 edge수를 +1하고, 정상적으로 삽입되었음을 알리는 메세지를 출력한다
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!새로운 edge (" + 
						edge.tailVertex() + "," + edge.headVertex() +") 가 그래프에 삽입되었습니다.");
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
			Iterator<Edge> neighborIterator = 
					this.graph().neighborIteratorOf(tailVertex);
			while(neighborIterator.hasNext()) {
				Edge nextEdge = neighborIterator.next();
				AppView.output(" " + nextEdge.headVertex());
			}
			AppView.outputLine("");
		}
	}
	
	private void showSortedResult() {
		AppView.outputLine("");
		AppView.outputLine("> 위상정렬의 결과는 다음과 같습니다:");
		List<Integer> topologicallySortedList = this.topologicalSort().topologicallySortedList();
		Iterator<Integer> iterator = topologicallySortedList.listIterator();
		while(iterator.hasNext()) {
			int vertex = iterator.next();
			AppView.output("-> " + vertex + " ");
		}
		AppView.outputLine("");
	}
	
	//vertex 수를 입력하도록 하는 메소드
	private int inputNumberOfVertices () {
		int numberOfVertices = AppView.inputNumberOfVertices();
		//올바른 수가 입력 될 때 까지 반복한다
		while (numberOfVertices <= 0) {
			AppView.outputLine("[오류] Vertex 수는 0 보다 커야 합니다." + numberOfVertices);
			numberOfVertices = AppView.inputNumberOfVertices();
		}
		return numberOfVertices ;
	}

	//edge의 수를 입력받는다
	private int inputNumberOfEdges() {
		int numberOfEdges = AppView.inputNumberOfEdges();
		//올바른 수가 입력될 때 까지 반복한다
		while (numberOfEdges < 0) {
			AppView.outputLine("[오류] edge 수는 0 보다 크거나 같아야 합니다." + numberOfEdges);
			numberOfEdges = AppView.inputNumberOfEdges();
		}
		return numberOfEdges;
	}
	
	private int inputSourceVertex() {
		int sourceVertex = AppView.inputSourceVertex();
		while(! this.graph().vertexDoesExist(sourceVertex)) {
			AppView.outputLine("[오류] 입력된 출발 vertex는 존재하지 않습니다: " + sourceVertex);
			sourceVertex = AppView.inputSourceVertex();
		}
		return sourceVertex;
	}
	
	public void run() {
		AppView.outputLine("<<< 위상정렬 프로그램을 시작합니다 >>>");
		//vertex 수, edge수를 입력받고 그래프를 생성한다
		this.inputAndMakeGraph();
		//생성된 그래프를 출력한다
		this.showGraph();
		
		if(this.topologicalSort().solve(this.graph()) ) {
			this.showSortedResult();
		}
		else {
			AppView.outputLine("");
			AppView.outputLine("[오류] 위상정렬을 성공적으로 마치지 못했습니다. 그래프에 사이클이 존재합니다.");
		}
		
		AppView.outputLine("");
		AppView.outputLine("<<< 위상정렬  프로그램을 종료합니다 >>>");
	}

}
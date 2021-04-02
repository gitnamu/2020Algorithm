package app;

import graph.WeightedUndirectedAdjacencyMatrixGraph;
import kruskal.MinCostSpanningTree;
import list.Iterator;
import list.List;
import graph.WeightedEdge;

public class AppController {
	// Private instance variables
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge>	_graph ;
	private MinCostSpanningTree										_minCostSpanningTree;
	private List<WeightedEdge>										_spanningTreeEdgeList;
	
	// Getters & Setters
	private WeightedUndirectedAdjacencyMatrixGraph graph() {
		return this._graph;
	}
	private void setGraph (WeightedUndirectedAdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}
	private MinCostSpanningTree minCostSpanningTree() {
		return this._minCostSpanningTree;
	}
	private void setMinCostSpanningTree(MinCostSpanningTree newMinCostSpanningTree) {
		this._minCostSpanningTree = newMinCostSpanningTree;
	}
	private List<WeightedEdge> spanningTreeEdgeList(){
		return _spanningTreeEdgeList;
	}
	private void setSpanningTreeEdgeList(List<WeightedEdge> newSpanningTreeEdgeList) {
		this._spanningTreeEdgeList = newSpanningTreeEdgeList;
	}
	
	// Constructor
	public AppController() {
		this.setGraph (null); 
		this.setMinCostSpanningTree(null);
		this.setSpanningTreeEdgeList(null);
	}
	// 최소비용 확장트리를 보여주는 메소드
	private void showMinCostSpanningTree() {
		AppView.outputLine("");
		AppView.outputLine("> 주어진 그래프의 최소비용 확장트리의 edge들은 다음과 같습니다: ");
		Iterator<WeightedEdge> listIterator = this.spanningTreeEdgeList().listIterator();
		while(listIterator.hasNext()) {
			WeightedEdge edge = listIterator.next();
			AppView.outputLine("Tree Edge(" + edge.tailVertex() + ", " + edge.headVertex() + 
						", (" + edge.weight() + "))");
		}
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
		this.setGraph(new WeightedUndirectedAdjacencyMatrixGraph(numberOfVertices));
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
			//각 vertex에 연결된 모든 edge들을 출력한다
			for(int headVertex = 0;headVertex < this.graph().numberOfVertices();headVertex++) {
				if(this.graph().edgeDoesExist(new WeightedEdge(tailVertex, headVertex))) {
					AppView.output(" "+headVertex);
					AppView.output("(" + this.graph().weightOfEdge(tailVertex, headVertex) + ")");
				}
			}
			AppView.outputLine("");
		}
		AppView.outputLine("");
		AppView.outputLine("> 입력된 그래프의 Adjacency Matrix는 다음과 같습니다: ");
		AppView.output("      ");
		for(int headVertex=0; headVertex < this.graph().numberOfVertices(); headVertex++) {
			AppView.output(String.format(" [%1s]", headVertex));
		}
		AppView.outputLine("");
		for(int tailVertex = 0;tailVertex < this.graph().numberOfVertices() ; tailVertex++) {
			AppView.output("["+tailVertex+"] ->");
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices() ; headVertex++) {
				int weight = this.graph().weightOfEdge(tailVertex, headVertex);
				AppView.output(String.format("%4d", weight));
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
	public void run() {
		AppView.outputLine("<<< 최소비용 확장 트리 찾기 프로그램을 시작합니다 >>>");
		//vertex 수, edge수를 입력받고 그래프를 생성한다
		this.inputAndMakeGraph();
		//생성된 그래프를 출력한다
		this.showGraph();
		
		AppView.outputLine("");
		AppView.outputLine("> 주어진 그래프의 최소비용 확장트리 찾기를 시작합니다:");
		AppView.outputLine("");
		this.setMinCostSpanningTree(new MinCostSpanningTree());
		this.setSpanningTreeEdgeList(this.minCostSpanningTree().solve(this.graph()));
		if(this.spanningTreeEdgeList() == null) {
			AppView.outputLine("> 주어진 그래프의 컴포넌트가 2개 이상이여서, 최소비용 확장트리 찾기를 실패하였습니다.");
		}
		else {
			this.showMinCostSpanningTree();
		}
		AppView.outputLine("");
		AppView.outputLine("<<< 최소비용 확장 트리 찾기 프로그램을 종료합니다 >>>");
	}

}
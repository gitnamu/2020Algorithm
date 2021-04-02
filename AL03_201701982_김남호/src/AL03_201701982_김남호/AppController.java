package AL03_201701982_김남호;

public class AppController {
	private AdjacencyMatrixGraph _graph ;
	private PairwiseDisjointSets _pairwiseDisjointSets;

	//_graph의 getter
	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	//_graph의 setter
	private void setGraph (AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}
	//_pairwiseDisjointSets의 getter
	private PairwiseDisjointSets pairwiseDisjointSets() {
		return this._pairwiseDisjointSets;
	}
	//_pairwiseDisjointSets의 setter
	private void setPairWiseDisjointSets(PairwiseDisjointSets newPairwiseDisjointSets) {
		this._pairwiseDisjointSets = newPairwiseDisjointSets;
	}
	//vertex수, edge수를 입력받고 그래프를 생성한다
	private void inputAndMakeGraph () {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다:");
		//vertex수를 입력받는다
		int numberOfVertices = this.inputNumberOfVertices();
		//vertex 수 크기의 graph를 생성한다
		this.setGraph(new AdjacencyMatrixGraph(numberOfVertices));
		//edge 수를 입력받는다
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		//집합을 생성한다
		this.initCycleDetection();
		
		int edgeCount = 0;
		//edge를 입력받는다
		while(edgeCount < numberOfEdges) {
			Edge edge = this.inputEdge();
			//입력된 edge가 이미 존재하는 edge이면 오류 메세지를 출력한다
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(오류) 입력된 edge ("+
					edge.tailVertex()+","+ edge.headVertex()+")는 그래프에 이미 존재합니다.");
			}
			else {
				//오류가 발생하지 않았으면 생성된 edge수를 +1하고, 정상적으로 삽입되었음을 알리는 메세지를 출력한다
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!새로운 edge ("+ 
					edge.tailVertex()+","+edge.headVertex()+") 가 그래프에 삽입되었습니다.");
				//만약 입력된 edge로 인해 사이클이 생성되었으면 사이클이 생성되었다는 메세지를 출력한다
				if(this.addeedEdgeDoesMakeCycle(edge)) {
					AppView.outputLine("![Cycle] 삽입된 edge ("+edge.tailVertex()+", "+
							edge.headVertex()+")는 그래프 사이클을 만들었습니다.");
				}
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
				if(this.graph().edgeDoesExist(new Edge(tailVertex, headVertex))) {
					AppView.output(" "+headVertex);
				}
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
	//vertex 수를 제공하여 PairwiseDisjointSets 객체를 생성한다
	private void initCycleDetection() {
		this.setPairWiseDisjointSets(
				new PairwiseDisjointSets(this.graph().numberOfVertices()));
	}
	//cycle이 만들어지는지 검사한다
	private boolean addeedEdgeDoesMakeCycle(Edge anAddedEdge) {
		//입력된 edge에서 tail vertex와 head vertex를 추출한다
		int tailVertex = anAddedEdge.tailVertex();
		int headVertex = anAddedEdge.headVertex();
		//각 vertex의 root를 find()를 통해 찾는다
		int setForTailVertex = this.pairwiseDisjointSets().find(tailVertex);
		int setForHeadVertex = this.pairwiseDisjointSets().find(headVertex);
		//만약 두 vertex의 root가 같으면 cycle이 만들어지므로 true를 반환한다
		if(setForTailVertex == setForHeadVertex) {
			return true;
		}
		else {
			//root가 같지 않다면 두 집합을 합치고 false를 반환한다
			this.pairwiseDisjointSets().union(setForTailVertex, setForHeadVertex);
			return false;
		}
	}
	public void run() {
		AppView.outputLine("<<< 그래프 만들기를 시작합니다 >>>");
		//vertex 수, edge수를 입력받고 그래프를 생성한다
		this.inputAndMakeGraph();
		//생성된 그래프를 출력한다
		this.showGraph();
		AppView.outputLine("");
		AppView.outputLine("<<< 그래프 만들기를 종료합니다 >>>");
	}
	//생성자. 그래프와 집합을 초기화한다
	public AppController() {
		this.setGraph (null); 
		this.setPairWiseDisjointSets(null);
	}
}
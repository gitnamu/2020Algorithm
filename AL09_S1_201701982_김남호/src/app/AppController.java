package app;

import equivalenceClasses.EquivalenceClasses;
import graph.AdjacencyGraph;
import graph.Edge;
import graph.UndirectedAdjacencyListGraph;
import list.Iterator;
import list.List;

public class AppController {
	
	// Private instance variables
	private AdjacencyGraph<Edge>		_graph ;
	private EquivalenceClasses<Edge>	_equivalenceClasses;
	
	// Getters & Setters
	private AdjacencyGraph<Edge> graph() {
		return this._graph;
	}
	private void setGraph (AdjacencyGraph<Edge> newGraph) {
		this._graph = newGraph;
	}
	private EquivalenceClasses<Edge> equivalenceClasses(){
		return this._equivalenceClasses;
	}
	private void setEquivalenceClasses(EquivalenceClasses<Edge> newEquivalenceClasses) {
		this._equivalenceClasses = newEquivalenceClasses;
	}
	
	// Constructor
	public AppController() {
		this.setGraph (null);
		this.setEquivalenceClasses(new EquivalenceClasses<Edge>());
	}
	
	//관계 쌍을 입력하도록 하는 메소드
	private Edge inputEdge() {
		do {
			//원소 두 개를 입력받는다
			AppView.outputLine("- 입력할 관계 쌍의 두 원소를 차례로 입력해야 합니다:");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			//입력받은 원소가 존재하는 원소인지 확인한다
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {
				//만약 두 원소가 동일한 원소이면 오류 메세지를 출력한다
				if(tailVertex == headVertex) {
					AppView.outputLine("[오류] 두 원소 번호가 동일합니다.");
				}
				//정상적인 원소 쌍이면 관계 쌍을 생성한다
				else {
					return (new Edge(tailVertex, headVertex));
				}
			}
			//존재하지 않는 원소이면 오류 메세지를 출력한다
			else {
				if(! this.graph().vertexDoesExist(tailVertex)) {
					AppView.outputLine("[오류] 존재하지 않는 첫 번째 원소 입니다: "+tailVertex);
				}
				if(! this.graph().vertexDoesExist(headVertex)) {
					AppView.outputLine("[오류] 존재하지 않는 두 번째 원소 입니다: "+headVertex);
				}
			}
		}while(true);	//정상적인 원소를 입력 받을 때까지 무한반복한다
	}
	
	//원소 개수, 관계 쌍 개수를 입력받고 그래프를 생성한다
	private void inputAndMakeGraph () {
		AppView.outputLine("> 입력할 동등 관계의 원소 수와 관계 쌍의 수를 먼저 입력해야 합니다:");
		//원소 개수를 입력받는다
		int numberOfVertices = this.inputNumberOfVertices();
		//원소 개수 크기의 graph를 생성한다
		this.setGraph(new UndirectedAdjacencyListGraph<Edge>(numberOfVertices));
		//관계 쌍 수를 입력받는다
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 관계 쌍을 주어진 수 만큼 입력합니다:");
		
		int edgeCount = 0;
		//원소를 입력받는다
		while(edgeCount < numberOfEdges) {
			Edge edge = this.inputEdge();
			//입력된 원소가 이미 존재하는 원소이면 오류 메세지를 출력한다
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(오류) 입력된 관계 쌍 ("+ 
			edge.tailVertex()+ "," +  edge.headVertex() + ") 는 그래프에 이미 존재합니다.");
			}
			else {
				//오류가 발생하지 않았으면 생성된 원소 개수를 +1하고, 정상적으로 삽입되었음을 알리는 메세지를 출력한다
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!새로운 관계 쌍 (" + 
						edge.tailVertex() + "," + edge.headVertex() +") 가 그래프에 삽입되었습니다.");
			}
		}
	}
	//입력된 그래프를 출력하는 그래프
	private void showGraph () {
		AppView.outputLine("");
		AppView.outputLine("> 입력된 관계 쌍의 그래프는 다음과 같습니다:");
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
	
	//vertex 수를 입력하도록 하는 메소드
	private int inputNumberOfVertices () {
		int numberOfVertices = AppView.inputNumberOfVertices();
		//올바른 수가 입력 될 때 까지 반복한다
		while (numberOfVertices <= 0) {
			AppView.outputLine("[오류] 원소 수는 0 보다 커야 합니다." + numberOfVertices);
			numberOfVertices = AppView.inputNumberOfVertices();
		}
		return numberOfVertices ;
	}

	//edge의 수를 입력받는다
	private int inputNumberOfEdges() {
		int numberOfEdges = AppView.inputNumberOfEdges();
		//올바른 수가 입력될 때 까지 반복한다
		while (numberOfEdges < 0) {
			AppView.outputLine("[오류] 관계 쌍의 수는 0 보다 크거나 같아야 합니다." + numberOfEdges);
			numberOfEdges = AppView.inputNumberOfEdges();
		}
		return numberOfEdges;
	}	
	
	private void showEquivalenceClasses() {
		AppView.outputLine("");
		AppView.outputLine("> 찾아진 동등 클래스는 다음과 같습니다.");
		// The next expression is independent of
		// the implementation of the list for the "Equivalence Classes".
		Iterator<List<Integer>> equivalenceClassListIterator = 
				this.equivalenceClasses().equivalenceClassList().listIterator();
		for(int classOrder=0; equivalenceClassListIterator.hasNext(); classOrder++) {
			AppView.output("[동등 클래스 " + String.format("%2d", classOrder) + "] ");
			
			// The next expression is independent of
			// the implementation of the list for the "Equivalence Class Elements".
			List<Integer> equivalenceClass =
					(List<Integer>) equivalenceClassListIterator.next();
			Iterator<Integer> equivalenceClassIterator = equivalenceClass.listIterator();
			AppView.output(String.format(" = {%2d", equivalenceClassIterator.next()));
			while(equivalenceClassIterator.hasNext()) {
				AppView.output(String.format(" ,%2d", equivalenceClassIterator.next()));
			}
			AppView.outputLine(" }");
		}
	}
	
	public void run() {
		AppView.outputLine("<<< 동등 클래스 찾기 프로그램을 시작합니다 >>>");
		//vertex 수, edge수를 입력받고 그래프를 생성한다
		this.inputAndMakeGraph();
		//생성된 그래프를 출력한다
		this.showGraph();
		
		if ( this.equivalenceClasses().solve(this.graph()) ) {
			this.showEquivalenceClasses();
		}
		else {
			AppView.outputLine("");
			AppView.outputLine("[오류] 동등 클래스를 성공적으로 마치지 못했습니다.");
		}
		AppView.outputLine("");
		AppView.outputLine("<<< 동등 클래스 찾기  프로그램을 종료합니다 >>>");
	}

}
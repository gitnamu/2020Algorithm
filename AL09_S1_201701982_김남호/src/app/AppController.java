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
	
	//���� ���� �Է��ϵ��� �ϴ� �޼ҵ�
	private Edge inputEdge() {
		do {
			//���� �� ���� �Է¹޴´�
			AppView.outputLine("- �Է��� ���� ���� �� ���Ҹ� ���ʷ� �Է��ؾ� �մϴ�:");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			//�Է¹��� ���Ұ� �����ϴ� �������� Ȯ���Ѵ�
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {
				//���� �� ���Ұ� ������ �����̸� ���� �޼����� ����Ѵ�
				if(tailVertex == headVertex) {
					AppView.outputLine("[����] �� ���� ��ȣ�� �����մϴ�.");
				}
				//�������� ���� ���̸� ���� ���� �����Ѵ�
				else {
					return (new Edge(tailVertex, headVertex));
				}
			}
			//�������� �ʴ� �����̸� ���� �޼����� ����Ѵ�
			else {
				if(! this.graph().vertexDoesExist(tailVertex)) {
					AppView.outputLine("[����] �������� �ʴ� ù ��° ���� �Դϴ�: "+tailVertex);
				}
				if(! this.graph().vertexDoesExist(headVertex)) {
					AppView.outputLine("[����] �������� �ʴ� �� ��° ���� �Դϴ�: "+headVertex);
				}
			}
		}while(true);	//�������� ���Ҹ� �Է� ���� ������ ���ѹݺ��Ѵ�
	}
	
	//���� ����, ���� �� ������ �Է¹ް� �׷����� �����Ѵ�
	private void inputAndMakeGraph () {
		AppView.outputLine("> �Է��� ���� ������ ���� ���� ���� ���� ���� ���� �Է��ؾ� �մϴ�:");
		//���� ������ �Է¹޴´�
		int numberOfVertices = this.inputNumberOfVertices();
		//���� ���� ũ���� graph�� �����Ѵ�
		this.setGraph(new UndirectedAdjacencyListGraph<Edge>(numberOfVertices));
		//���� �� ���� �Է¹޴´�
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> �������� ���� ���� �־��� �� ��ŭ �Է��մϴ�:");
		
		int edgeCount = 0;
		//���Ҹ� �Է¹޴´�
		while(edgeCount < numberOfEdges) {
			Edge edge = this.inputEdge();
			//�Էµ� ���Ұ� �̹� �����ϴ� �����̸� ���� �޼����� ����Ѵ�
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(����) �Էµ� ���� �� ("+ 
			edge.tailVertex()+ "," +  edge.headVertex() + ") �� �׷����� �̹� �����մϴ�.");
			}
			else {
				//������ �߻����� �ʾ����� ������ ���� ������ +1�ϰ�, ���������� ���ԵǾ����� �˸��� �޼����� ����Ѵ�
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!���ο� ���� �� (" + 
						edge.tailVertex() + "," + edge.headVertex() +") �� �׷����� ���ԵǾ����ϴ�.");
			}
		}
	}
	//�Էµ� �׷����� ����ϴ� �׷���
	private void showGraph () {
		AppView.outputLine("");
		AppView.outputLine("> �Էµ� ���� ���� �׷����� ������ �����ϴ�:");
		//��� vertex���� ����Ѵ�
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
	
	//vertex ���� �Է��ϵ��� �ϴ� �޼ҵ�
	private int inputNumberOfVertices () {
		int numberOfVertices = AppView.inputNumberOfVertices();
		//�ùٸ� ���� �Է� �� �� ���� �ݺ��Ѵ�
		while (numberOfVertices <= 0) {
			AppView.outputLine("[����] ���� ���� 0 ���� Ŀ�� �մϴ�." + numberOfVertices);
			numberOfVertices = AppView.inputNumberOfVertices();
		}
		return numberOfVertices ;
	}

	//edge�� ���� �Է¹޴´�
	private int inputNumberOfEdges() {
		int numberOfEdges = AppView.inputNumberOfEdges();
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while (numberOfEdges < 0) {
			AppView.outputLine("[����] ���� ���� ���� 0 ���� ũ�ų� ���ƾ� �մϴ�." + numberOfEdges);
			numberOfEdges = AppView.inputNumberOfEdges();
		}
		return numberOfEdges;
	}	
	
	private void showEquivalenceClasses() {
		AppView.outputLine("");
		AppView.outputLine("> ã���� ���� Ŭ������ ������ �����ϴ�.");
		// The next expression is independent of
		// the implementation of the list for the "Equivalence Classes".
		Iterator<List<Integer>> equivalenceClassListIterator = 
				this.equivalenceClasses().equivalenceClassList().listIterator();
		for(int classOrder=0; equivalenceClassListIterator.hasNext(); classOrder++) {
			AppView.output("[���� Ŭ���� " + String.format("%2d", classOrder) + "] ");
			
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
		AppView.outputLine("<<< ���� Ŭ���� ã�� ���α׷��� �����մϴ� >>>");
		//vertex ��, edge���� �Է¹ް� �׷����� �����Ѵ�
		this.inputAndMakeGraph();
		//������ �׷����� ����Ѵ�
		this.showGraph();
		
		if ( this.equivalenceClasses().solve(this.graph()) ) {
			this.showEquivalenceClasses();
		}
		else {
			AppView.outputLine("");
			AppView.outputLine("[����] ���� Ŭ������ ���������� ��ġ�� ���߽��ϴ�.");
		}
		AppView.outputLine("");
		AppView.outputLine("<<< ���� Ŭ���� ã��  ���α׷��� �����մϴ� >>>");
	}

}
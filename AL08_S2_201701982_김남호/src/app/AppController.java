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
	
	//edge�� �Է��ϵ��� �ϴ� �޼ҵ�
	private Edge inputEdge() {
		do {
			//vertex �ΰ��� �Է¹޴´�
			AppView.outputLine("- �Է��� edge�� �� vertex�� ���ʷ� �Է��ؾ� �մϴ�:");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			//�Է¹��� vertex�� �����ϴ� vertex���� Ȯ���Ѵ�
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {
				//���� �� vertex�� ������ vertex�̸� ���� �޼����� ����Ѵ�
				if(tailVertex == headVertex) {
					AppView.outputLine("[����] �� vertex ��ȣ�� �����մϴ�.");
				}
				//�������� vertex���̸� �ش� vertex���� tail, head�� �ϴ� edge�� �����Ѵ�
				else {
					return (new Edge(tailVertex, headVertex));
				}
			}
			//tail vertex �Ǵ� head vertex�� �������� �ʴ� vertex�̸� ���� �޼����� ����Ѵ�
			else {
				if(! this.graph().vertexDoesExist(tailVertex)) {
					AppView.outputLine("[����] �������� �ʴ� tail vertex �Դϴ�: "+tailVertex);
				}
				if(! this.graph().vertexDoesExist(headVertex)) {
					AppView.outputLine("[����] �������� �ʴ� head vertex �Դϴ�: "+headVertex);
				}
			}
		}while(true);	//�������� vertex�� �Է� ���� ������ ���ѹݺ��Ѵ�
	}
	
	//vertex��, edge���� �Է¹ް� �׷����� �����Ѵ�
	private void inputAndMakeGraph () {
		AppView.outputLine("> �Է��� �׷����� vertex ���� edge ���� ���� �Է��ؾ� �մϴ�:");
		//vertex���� �Է¹޴´�
		int numberOfVertices = this.inputNumberOfVertices();
		//vertex �� ũ���� graph�� �����Ѵ�
		this.setGraph(new DirectedAdjacencyListGraph<Edge>(numberOfVertices));
		//edge ���� �Է¹޴´�
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> �������� edge�� �־��� �� ��ŭ �Է��մϴ�.");
		
		int edgeCount = 0;
		//edge�� �Է¹޴´�
		while(edgeCount < numberOfEdges) {
			Edge edge = this.inputEdge();
			//�Էµ� edge�� �̹� �����ϴ� edge�̸� ���� �޼����� ����Ѵ�
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(����) �Էµ� edge ("+ 
			edge.tailVertex()+ "," +  edge.headVertex() + ") �� �׷����� �̹� �����մϴ�.");
			}
			else {
				//������ �߻����� �ʾ����� ������ edge���� +1�ϰ�, ���������� ���ԵǾ����� �˸��� �޼����� ����Ѵ�
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!���ο� edge (" + 
						edge.tailVertex() + "," + edge.headVertex() +") �� �׷����� ���ԵǾ����ϴ�.");
			}
		}
	}
	//�Էµ� �׷����� ����ϴ� �׷���
	private void showGraph () {
		AppView.outputLine("");
		AppView.outputLine("> �Էµ� �׷����� ������ �����ϴ�:");
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
	
	private void showSortedResult() {
		AppView.outputLine("");
		AppView.outputLine("> ���������� ����� ������ �����ϴ�:");
		List<Integer> topologicallySortedList = this.topologicalSort().topologicallySortedList();
		Iterator<Integer> iterator = topologicallySortedList.listIterator();
		while(iterator.hasNext()) {
			int vertex = iterator.next();
			AppView.output("-> " + vertex + " ");
		}
		AppView.outputLine("");
	}
	
	//vertex ���� �Է��ϵ��� �ϴ� �޼ҵ�
	private int inputNumberOfVertices () {
		int numberOfVertices = AppView.inputNumberOfVertices();
		//�ùٸ� ���� �Է� �� �� ���� �ݺ��Ѵ�
		while (numberOfVertices <= 0) {
			AppView.outputLine("[����] Vertex ���� 0 ���� Ŀ�� �մϴ�." + numberOfVertices);
			numberOfVertices = AppView.inputNumberOfVertices();
		}
		return numberOfVertices ;
	}

	//edge�� ���� �Է¹޴´�
	private int inputNumberOfEdges() {
		int numberOfEdges = AppView.inputNumberOfEdges();
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while (numberOfEdges < 0) {
			AppView.outputLine("[����] edge ���� 0 ���� ũ�ų� ���ƾ� �մϴ�." + numberOfEdges);
			numberOfEdges = AppView.inputNumberOfEdges();
		}
		return numberOfEdges;
	}
	
	private int inputSourceVertex() {
		int sourceVertex = AppView.inputSourceVertex();
		while(! this.graph().vertexDoesExist(sourceVertex)) {
			AppView.outputLine("[����] �Էµ� ��� vertex�� �������� �ʽ��ϴ�: " + sourceVertex);
			sourceVertex = AppView.inputSourceVertex();
		}
		return sourceVertex;
	}
	
	public void run() {
		AppView.outputLine("<<< �������� ���α׷��� �����մϴ� >>>");
		//vertex ��, edge���� �Է¹ް� �׷����� �����Ѵ�
		this.inputAndMakeGraph();
		//������ �׷����� ����Ѵ�
		this.showGraph();
		
		if(this.topologicalSort().solve(this.graph()) ) {
			this.showSortedResult();
		}
		else {
			AppView.outputLine("");
			AppView.outputLine("[����] ���������� ���������� ��ġ�� ���߽��ϴ�. �׷����� ����Ŭ�� �����մϴ�.");
		}
		
		AppView.outputLine("");
		AppView.outputLine("<<< ��������  ���α׷��� �����մϴ� >>>");
	}

}
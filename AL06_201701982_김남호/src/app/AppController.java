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
	// �ּҺ�� Ȯ��Ʈ���� �����ִ� �޼ҵ�
	private void showMinCostSpanningTree() {
		AppView.outputLine("");
		AppView.outputLine("> �־��� �׷����� �ּҺ�� Ȯ��Ʈ���� edge���� ������ �����ϴ�: ");
		Iterator<WeightedEdge> listIterator = this.spanningTreeEdgeList().listIterator();
		while(listIterator.hasNext()) {
			WeightedEdge edge = listIterator.next();
			AppView.outputLine("Tree Edge(" + edge.tailVertex() + ", " + edge.headVertex() + 
						", (" + edge.weight() + "))");
		}
	}
	
	//edge�� �Է��ϵ��� �ϴ� �޼ҵ�
	private WeightedEdge inputEdge() {
		do {
			//vertex �ΰ��� �Է¹޴´�
			AppView.outputLine("- �Է��� edge�� �� vertex�� cost�� ���ʷ� �Է��ؾ� �մϴ�:");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			int cost = AppView.inputCost();
			//�Է¹��� vertex�� �����ϴ� vertex���� Ȯ���Ѵ�
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex) && cost>0) {
				//���� �� vertex�� ������ vertex�̸� ���� �޼����� ����Ѵ�
				if(tailVertex == headVertex) {
					AppView.outputLine("[����] �� vertex ��ȣ�� �����մϴ�.");
				}
				//�������� vertex���̸� �ش� vertex���� tail, head�� �ϴ� edge�� �����Ѵ�
				else {
					return (new WeightedEdge(tailVertex, headVertex, cost));
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
				if(cost<0) {
					AppView.outputLine("[����] edge�� ������ ����̾�� �մϴ�: "+cost);
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
		this.setGraph(new WeightedUndirectedAdjacencyMatrixGraph(numberOfVertices));
		//edge ���� �Է¹޴´�
		int numberOfEdges = this.inputNumberOfEdges();
		AppView.outputLine("");
		AppView.outputLine("> �������� edge�� �־��� �� ��ŭ �Է��մϴ�.");
		
		int edgeCount = 0;
		//edge�� �Է¹޴´�
		while(edgeCount < numberOfEdges) {
			WeightedEdge edge = this.inputEdge();
			//�Էµ� edge�� �̹� �����ϴ� edge�̸� ���� �޼����� ����Ѵ�
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(����) �Էµ� edge ("+ edge.tailVertex()+ "," +  edge.headVertex() + 
						", (" + edge.weight() + "))�� �׷����� �̹� �����մϴ�.");
			}
			else {
				//������ �߻����� �ʾ����� ������ edge���� +1�ϰ�, ���������� ���ԵǾ����� �˸��� �޼����� ����Ѵ�
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!���ο� edge (" + edge.tailVertex() + "," + edge.headVertex() +
						", (" + edge.weight() + ")) �� �׷����� ���ԵǾ����ϴ�.");
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
			//�� vertex�� ����� ��� edge���� ����Ѵ�
			for(int headVertex = 0;headVertex < this.graph().numberOfVertices();headVertex++) {
				if(this.graph().edgeDoesExist(new WeightedEdge(tailVertex, headVertex))) {
					AppView.output(" "+headVertex);
					AppView.output("(" + this.graph().weightOfEdge(tailVertex, headVertex) + ")");
				}
			}
			AppView.outputLine("");
		}
		AppView.outputLine("");
		AppView.outputLine("> �Էµ� �׷����� Adjacency Matrix�� ������ �����ϴ�: ");
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
	//vertex ���� �Է��ϵ��� �ϴ� �޼ҵ�
	private int inputNumberOfVertices () {
		//�ùٸ� ���� �Է� �� �� ���� �ݺ��Ѵ�
		while (true) {
			AppView.output ("? Vertex ���� �Է��Ͻÿ�: ") ;
			//�Էµ� ���� int������ ����ȯ�� �Ұ����ϰ� 1���� ������ ���� �޼����� ����Ѵ�
			try {
				int numberOfVertices = AppView.inputInt() ;
				if ( numberOfVertices < 1 ) {
					AppView.output ("[����] Vertex ���� 0 ���� Ŀ�� �մϴ�.");
				}
				else {
					return numberOfVertices ;	
				}
			}
			catch (NumberFormatException e) {
				AppView.outputLine ("[����] �ùٸ� ���ڰ� �Էµ��� �ʾҽ��ϴ�.");
			}
		}
	}

	//edge�� ���� �Է¹޴´�
	private int inputNumberOfEdges () {
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while (true) {
			AppView.output ("? Edge ���� �Է��Ͻÿ�: ") ;
			//�Է¹��� ���� int���� �ƴϰ� 0���� ������� ���� �޼����� ����Ѵ�
			try {
				int numberOfEdges = AppView.inputInt() ;
				if ( numberOfEdges < 0 ) {
					AppView.output ("[����] Edge ���� 0 ���� ũ�ų� ���ƾ� �մϴ�.") ;
				}
				else {
					return numberOfEdges ;
				}
			}
			catch (NumberFormatException e) {
				AppView.outputLine ("[����] �ùٸ� ���ڰ� �Էµ��� �ʾҽ��ϴ�.") ;
			}
		}
	}
	public void run() {
		AppView.outputLine("<<< �ּҺ�� Ȯ�� Ʈ�� ã�� ���α׷��� �����մϴ� >>>");
		//vertex ��, edge���� �Է¹ް� �׷����� �����Ѵ�
		this.inputAndMakeGraph();
		//������ �׷����� ����Ѵ�
		this.showGraph();
		
		AppView.outputLine("");
		AppView.outputLine("> �־��� �׷����� �ּҺ�� Ȯ��Ʈ�� ã�⸦ �����մϴ�:");
		AppView.outputLine("");
		this.setMinCostSpanningTree(new MinCostSpanningTree());
		this.setSpanningTreeEdgeList(this.minCostSpanningTree().solve(this.graph()));
		if(this.spanningTreeEdgeList() == null) {
			AppView.outputLine("> �־��� �׷����� ������Ʈ�� 2�� �̻��̿���, �ּҺ�� Ȯ��Ʈ�� ã�⸦ �����Ͽ����ϴ�.");
		}
		else {
			this.showMinCostSpanningTree();
		}
		AppView.outputLine("");
		AppView.outputLine("<<< �ּҺ�� Ȯ�� Ʈ�� ã�� ���α׷��� �����մϴ� >>>");
	}

}
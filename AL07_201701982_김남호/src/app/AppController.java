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
		this.setGraph(new WeightedDirectedAdjacencyListGraph<WeightedEdge>(numberOfVertices));
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
	
	private int inputSourceVertex() {
		int sourceVertex = AppView.inputSourceVertex();
		while(! this.graph().vertexDoesExist(sourceVertex)) {
			AppView.outputLine("[����] �Էµ� ��� vertex�� �������� �ʽ��ϴ�: " + sourceVertex);
			sourceVertex = AppView.inputSourceVertex();
		}
		return sourceVertex;
	}
	
	private void solveAndShowShortestPaths() {
		AppView.outputLine("");
		AppView.outputLine("> �־��� �׷������� �ִ� ��θ� ã���ϴ�:");
		if(this.graph().numberOfVertices() <= 1) {
			AppView.outputLine("[����] vertex ��(" + this.graph().numberOfVertices() +
						") �� �ʹ� ���, �ִܰ�� ã�⸦ ���� �ʽ��ϴ�. 2�� �̻��̾�� �մϴ�.");
		}
		else {
			AppView.outputLine("> ������� �Է��ؾ� �մϴ�:");
			int sourceVertex = this.inputSourceVertex();
			if(this.shortestPaths().solve(this.graph(), sourceVertex)) {
				AppView.outputLine("");
				AppView.outputLine("> �ִ� ��κ� ���� ��δ� ������ �����ϴ�:");
				AppView.outputLine("�����=" + sourceVertex + ":");
				for(int destination = 0;destination < this.graph().numberOfVertices();destination++) {
					if(destination != sourceVertex) {
						AppView.output("  [������=" + destination + "] ");
						AppView.output("�ּҺ��=" + this.shortestPaths().minCostOfPathToDestination(destination) + ", ");
						AppView.output("���:");
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
				AppView.outputLine("[����] �ִܰ�� ã�⸦ �����Ͽ����ϴ�.");
			}
		}
	}
	
	public void run() {
		AppView.outputLine("<<< �ִܰ�� ã�� ���α׷��� �����մϴ� >>>");
		//vertex ��, edge���� �Է¹ް� �׷����� �����Ѵ�
		this.inputAndMakeGraph();
		//������ �׷����� ����Ѵ�
		this.showGraph();
		
		this.solveAndShowShortestPaths();
		AppView.outputLine("");
		AppView.outputLine("<<< �ִܰ�� ã�� ���α׷��� �����մϴ� >>>");
	}

}
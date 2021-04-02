package AL04_201701982_�賲ȣ;

public class AppController {
	private AdjacencyMatrixGraph _graph ;
	private Coloring _coloring;
	
	//_coloring�� getter
	private Coloring coloring() {
		return this._coloring;
	}
	//_coloring�� setter
	private void setColoring(Coloring aColoring) {
		this._coloring = aColoring;
	}
	//_graph�� getter
	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	//_graph�� setter
	private void setGraph (AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}

	//vertex��, edge���� �Է¹ް� �׷����� �����Ѵ�
	private void inputAndMakeGraph () {
		AppView.outputLine("> �Է��� �׷����� vertex ���� edge ���� ���� �Է��ؾ� �մϴ�:");
		//vertex���� �Է¹޴´�
		int numberOfVertices = this.inputNumberOfVertices();
		//vertex �� ũ���� graph�� �����Ѵ�
		this.setGraph(new AdjacencyMatrixGraph(numberOfVertices));
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
					edge.tailVertex()+","+ edge.headVertex()+")�� �׷����� �̹� �����մϴ�.");
			}
			else {
				//������ �߻����� �ʾ����� ������ edge���� +1�ϰ�, ���������� ���ԵǾ����� �˸��� �޼����� ����Ѵ�
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("!���ο� edge ("+ 
					edge.tailVertex()+","+edge.headVertex()+") �� �׷����� ���ԵǾ����ϴ�.");
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
				if(this.graph().edgeDoesExist(new Edge(tailVertex, headVertex))) {
					AppView.output(" "+headVertex);
				}
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
	//ĥ���� ������ ����ϴ� �޼ҵ�
	private void showColoring() {
		AppView.outputLine("");
		AppView.outputLine("> �� vertex�� ĥ���� ������ ������ �����ϴ�:");
		// ��� vertex�� ���� ������ ����Ѵ�
		for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++) {
			AppView.outputLine("[" + vertex + "]" + this.coloring().vertexColor(vertex).name());
		}
		
		AppView.outputLine("");
		AppView.outputLine("> �� �� vertex�� ������ ���� edge���� ������ �����ϴ�:");
		//�� �� ���� ���� vertex�� ������ ��� �ٸ��ٴ� �޼����� ����Ѵ�
		if(this.coloring().sameColorEdges().size() == 0) {
			AppView.outputLine("!! ��� edge�� �� �� vertex�� ������ �ٸ��ϴ�.");
		}
		else {
			//���Ḯ��Ʈ�� �ݺ��ڸ� �����Ѵ�
			LinkedList<Edge>.IteratorForLinkedList iterator = this.coloring().sameColorEdges().iterator();
			//�ݺ����� next�� ���������� �ݺ��Ѵ�
			while(iterator.hasNext()) {
				Edge currentEdge = iterator.next();	//iterator�� �̵���Ű�� ��ȯ�� ���� �����Ѵ�
				// ��ȯ ���� edge�� vertex�� ������ ����Ѵ�
				AppView.output("(" + currentEdge.tailVertex() + "," + currentEdge.headVertex() + "):");
				AppView.outputLine(" " + this.coloring().vertexColor(currentEdge.tailVertex()).name());
			}
		}
	}
	public void run() {
		AppView.outputLine("<<< �׷��� ��ĥ�ϱ⸦ �����մϴ� >>>");
		//vertex ��, edge���� �Է¹ް� �׷����� �����Ѵ�
		this.inputAndMakeGraph();
		//������ �׷����� ����Ѵ�
		this.showGraph();
		
		this.setColoring(new Coloring(this.graph()));
		this.coloring().runColoring();
		this.showColoring();
		
		AppView.outputLine("");
		AppView.outputLine("<<< ��ĥ�ϱ⸦ �����մϴ� >>>");
	}
	//������. �׷����� ������ �ʱ�ȭ�Ѵ�
	public AppController() {
		this.setGraph (null); 
		this.setColoring(null);
	}
}
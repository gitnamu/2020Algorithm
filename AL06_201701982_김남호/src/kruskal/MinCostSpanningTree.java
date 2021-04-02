package kruskal;

import app.AppView;
import graph.WeightedUndirectedAdjacencyMatrixGraph;
import graph.WeightedEdge;
import list.LinkedList;
import list.List;

public class MinCostSpanningTree {
	
	// Private instance variables
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge>	_graph;
	private MinPriorityQ<WeightedEdge>								_minPriorityQ;
	private List<WeightedEdge>										_spanningTreeEdgeList;
	
	// Getters & Setters
	private MinPriorityQ<WeightedEdge> minPriorityQ(){
		return this._minPriorityQ;
	}
	private void setMinPriorityQ(MinPriorityQ<WeightedEdge> newMinPriorityQ) {
		this._minPriorityQ = newMinPriorityQ;
	}
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> graph(){
		return this._graph;
	}
	private void setGraph(WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> newGraph) {
		this._graph = newGraph;
	}
	private List<WeightedEdge> spanningTreeEdgeList(){
		return this._spanningTreeEdgeList;
	}
	private void setSpanningTreeEdgeList(List<WeightedEdge> newSpanningTreeEdgeList) {
		this._spanningTreeEdgeList = newSpanningTreeEdgeList;
	}
	
	// Constructor
	public MinCostSpanningTree() {
		this.setGraph(null);
		this.setMinPriorityQ(null);
		this.setSpanningTreeEdgeList(null);
	}
	// 현재 주어진 그래프에서 edge들을 모두 얻어, Min Priority Queue에 삽입
	private void initMinPriorityQ() {
		this.setMinPriorityQ(new MinPriorityQ<WeightedEdge>(this.graph().numberOfEdges()));
		int numberOfVertices = this.graph().numberOfVertices();
		// 그래프의 모든 edge를  minPriorityQ()에 삽입
		for(int tailVertex = 0; tailVertex < numberOfVertices; tailVertex++) {
			for(int headVertex = tailVertex+1; headVertex < numberOfVertices; headVertex++) {
				if(this.graph().edgeDoesExist(tailVertex, headVertex)) {
					int weight = this.graph().weightOfEdge(tailVertex, headVertex);
					WeightedEdge edge = new WeightedEdge(tailVertex, headVertex, weight);
					this.minPriorityQ().add(edge);
				}
			}
		}
	}
	// Kruskal 알고리즘 구현
	public List<WeightedEdge> solve(WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> aGraph){
		this.setGraph(aGraph);
		this.initMinPriorityQ();
		this.setSpanningTreeEdgeList(new LinkedList<WeightedEdge>());
		
		PairwiseDisjointSets pairwiseDisjointSets = 
				new PairwiseDisjointSets(this.graph().numberOfVertices());
		int maxNumberOfTreeEdges = this.graph().numberOfVertices() - 1;
		while((this.spanningTreeEdgeList().size() < maxNumberOfTreeEdges) &&
				(! this.minPriorityQ().isEmpty()))
		{
			WeightedEdge edge = this.minPriorityQ().removeMin();
			int setOfTailVertex = pairwiseDisjointSets.find(edge.tailVertex());
			int setOfHeadVertex = pairwiseDisjointSets.find(edge.headVertex());
			if(setOfTailVertex == setOfHeadVertex) {
				AppView.outputLine("[Debug] Edge("+ edge.tailVertex() + ", " + edge.headVertex() +
							", " + edge.weight() + "))는 스패닝 트리에 사이클을 생성시키므로, 버립니다.");
			}
			else {
				this.spanningTreeEdgeList().add(edge);
				pairwiseDisjointSets.union(setOfTailVertex, setOfHeadVertex);
				AppView.outputLine("[Debug] Edge(" + edge.tailVertex() + ", " + edge.headVertex() +
						", (" + edge.weight() + "))는 스패닝 트리의 edge로 추가됩니다.");
			}
		}
		return (this.spanningTreeEdgeList().size() == maxNumberOfTreeEdges)?
				this.spanningTreeEdgeList():null;
	}
}

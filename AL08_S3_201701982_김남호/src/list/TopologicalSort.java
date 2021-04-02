package list;

import app.AppView;
import graph.AdjacencyGraph;
import graph.Edge;
import list.List;
import list.StackWithIterator;
import list.Iterator;
import list.ArrayList;
import list.LinkedStackWithIterator;

public class TopologicalSort<E extends Edge> {
	
	// Constants
	private static final boolean DEBUG_MODE = true;
	
	private static void showDebugMessage(String aMessage) {
		// 구현하시오
	}
	
	// Private instance variables
	private AdjacencyGraph<E>			_graph;
	private int[]						_predecessorCounts;
	private StackWithIterator<Integer>	_zeroCountVertices;
	private List<Integer>				_sortedList;
	
	// Getters & Setters
	private AdjacencyGraph<E> graph(){
		return this._graph;
	}
	private void setGraph(AdjacencyGraph<E> newGraph) {
		this._graph = newGraph;
	}
	private int[] predecessorCounts() {
		return this._predecessorCounts;
	}
	private void setPredecessorCounts(int[] newPredecessorCounts) {
		this._predecessorCounts = newPredecessorCounts;
	}
	private StackWithIterator<Integer> zeroCountVetices(){
		return this._zeroCountVertices;
	}
	private void setZeroCountVetices(StackWithIterator<Integer> newZeroCountVetices) {
		this._zeroCountVertices = newZeroCountVetices;
	}
	private List<Integer> sortedList(){
		return this._sortedList;
	}
	private void setSortedList(List<Integer> newSortedList) {
		this._sortedList = newSortedList;
	}
	
	// public methods for the sorting result
	public List<Integer> topologicallySortedList(){
		return this.sortedList();
	}
	
	// Constructor
	public TopologicalSort() {
		this.setGraph(null);
		this.setPredecessorCounts(null);
		this.setZeroCountVetices(null);
		this.setSortedList(null);
	}
	
	private void initPredcessorCounts() {
		this.setPredecessorCounts(new int[this.graph().numberOfVertices()]);
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices();tailVertex++) {
			this.predecessorCounts()[tailVertex] = 0;
		}
		for(int tailVertex = 0;tailVertex<this.graph().numberOfVertices(); tailVertex++) {
			Iterator<E> iterator = 
					this.graph().neighborIteratorOf(tailVertex);
			while(!iterator.hasNext()) {
				E edge = iterator.next();
				this.predecessorCounts()[edge.headVertex()]++;
			}
		}
		TopologicalSort.showDebugMessage("\n[Debug] 각 vertex의 초기 선행자 수는 다음과 같습니다:\n-->");
		for(int vertex = 0;vertex < this.graph().numberOfVertices();vertex++) {
			TopologicalSort.showDebugMessage("[" + vertex + "]=" + this.predecessorCounts()[vertex]);
		}
		TopologicalSort.showDebugMessage("\n");
	}
	
	private void initZeroCountVertices() {
		this.setZeroCountVetices(new LinkedStackWithIterator<Integer>());
		TopologicalSort.showDebugMessage(
				"\n [Debug] 그래프에 선행자가 없는 vertex들은 다음과 같습니다:\n--> (");
		for(int vertex = 0;vertex < this.graph().numberOfVertices();vertex++) {
			if(this.predecessorCounts()[vertex] == 0) {
				this.zeroCountVetices().push(vertex);
				TopologicalSort.showDebugMessage(vertex + " ");
			}
		}
		TopologicalSort.showDebugMessage("\n");
	}
	
	// Only for Debugging
	private void showZeroCountVertices() {
		TopologicalSort.showDebugMessage("--> 스택: <Top>");
		Iterator<Integer> iterator = this.zeroCountVetices().iterator();
		while(iterator.hasNext()) {
			int vertex = (int) iterator.next();
			TopologicalSort.showDebugMessage(" " + vertex);
		}
		TopologicalSort.showDebugMessage(" <Bottom>\n ");
	}
	
	// Public methods
	public boolean solve(AdjacencyGraph<E> aGraph) {
		this.setGraph(aGraph);
		this.initPredcessorCounts();
		this.initZeroCountVertices();
		this.setSortedList(new ArrayList<Integer>(this.graph().numberOfVertices()));
		
		TopologicalSort.showDebugMessage("\n[Debug] 스택에 pop/push 되는 과정은 다음과 같습니다:\n");
		this.showZeroCountVertices();
		while(! this.zeroCountVetices().isEmpty()) {
			int tailVertex = this.zeroCountVetices().pop();
			TopologicalSort.showDebugMessage("--> Popped = " + tailVertex + ": Pushed = (");
			this.sortedList().add(tailVertex);
			Iterator<E> iterator = 
					this.graph().neighborIteratorOf(tailVertex);
			while (iterator.hasNext()) {
				E edge = iterator.next();
				--this.predecessorCounts()[edge.headVertex()];
				if(this.predecessorCounts()[edge.headVertex()] == 0) {
					this.zeroCountVetices().push(edge.headVertex());
					TopologicalSort.showDebugMessage(edge.headVertex()+" ");
				}
			}
			TopologicalSort.showDebugMessage(")\n");
			this.showZeroCountVertices();
		}
		return (this.sortedList().size() == this.graph().numberOfVertices());
	}
}

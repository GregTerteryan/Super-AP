package pkg;
import java.util.Scanner;
import java.util.Random;


public class Node {
	Node next;
	char data;

	public Node(char data) {
		next = null;
		this.data = data;
	}
	public void setNext(Node next){
		this.next = next;
	}
	public char getData(){
		return data;
	}	
	public Node getNext(){
		return next;
	}
}

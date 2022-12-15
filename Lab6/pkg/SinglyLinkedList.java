package pkg;
import java.util.Scanner;
import java.util.Random;


public class SinglyLinkedList {
	Node head;

	/* 
		Postcondition: The head will be null 
	*/
	public SinglyLinkedList() {
		head = null;
	}

	/* 
		Receives an integer position, searches through the SinglyLinkedList for the position and returns the data at that positon
	   	If the position doesn't exist, it returns -1
	*/ 
	public int get(int pos){
		Node n = head;
		for (int c = 0; c < pos; c++) {
			try {
				n = n.getNext();
			}
			catch (NullPointerException L) {
				return -1;
			}
		}
		return n.getData();
	}

	/*
		Insert a new Node at the given position with the data given
	*/
	public void insert(int pos, int data) {
		Node n = head;
		if (pos == 0) {
			n = new Node(data);
			n.setNext(head);
			head = n;
		}
		else {
			for (int c = 0; c < pos - 1; c++) {
				try {
					n = n.getNext();
				}
				catch (NullPointerException bruh) {
					break;
				}
			}
			try {
				Node temp = n.getNext();
				n.setNext(new Node(data));
				n = n.getNext();
				n.setNext(temp);
			}
			catch (NullPointerException e) {
				head = new Node(data);
			}
		}
	}

	/*
		Remove the node at the given position
		If no position exists, don't change the list
	*/
	public void remove(int pos){
		Node n = head;
		for (int c = 0; c < pos - 1; c++) {
			try {
				n = n.getNext();
			}
			catch (NullPointerException e) {
				return;
			}
		}
		n.setNext(n.getNext().getNext());
	}

	/*
		Swap two Nodes with the two positions given
	*/
	public void swap(int pos1, int pos2) {
		if (pos1 == pos2) {
			return;
		}
		Node a = head;
		for (int c = 0; c < pos1 - 1; c++) {
			if (a.getNext() != null) {
				a = a.getNext();
			}
			else {
				return;
			}
		}
		Node b = head;
		for (int d = 0; d < pos2 - 1; d++) {
			if (b.getNext() != null) {
				b = b.getNext();
			}
			else {
				return;
			}
		}
		Node temp;
		if (pos1 == 0) {
			//a is still head
			temp = b.getNext().getNext();
			head = b.getNext();
			b.setNext(a);
			head.setNext(a.getNext());
			b.getNext().setNext(temp);
		}
		else if (pos2 == 0) {
			//b is still head
			temp = a.getNext().getNext();
			head = a.getNext();
			a.setNext(b);
			head.setNext(b.getNext());
			a.getNext().setNext(temp);
		}
		else {
			temp = a.getNext();
			Node temp2 = b.getNext();
			a.setNext(temp2);
			b.setNext(temp);
			Node temp3 = temp.getNext();
			Node temp4 = temp2.getNext();
			temp.setNext(temp4);
			temp2.setNext(temp3);
		}
	}

	/*
		Print all data values in the LinkedList 
	*/
	public void printList(){
		Node n = head;
		while (true) {
			System.out.println(n);
			if (n.getNext() != null) {
				n = n.getNext();
			}
			else {
				return;
			}
		}
	}
}

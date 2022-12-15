package pkg;
import java.util.Scanner;
import java.util.Random;


public class Stack {
	/*  LAST IN FIRST OUT  */
	Node top;

	/* 
		Postcondition: The top will be null.
	*/
	public Stack() {
		top = null;
	}

	/*
		Insert a new Node on top of the stack
	*/
	public void push(char data){
		Node n = new Node(data);
		n.setNext(top);
		top = n;
	}

	/*
		Removes the top node of the stack
	*/
	public char pop(){
		char data = top.getData();
		top = top.getNext();
		return data;
	}

	/*
		Returns the top value of the stack. Doesn't pop. 
	*/
	public char peek(){
		return top.getData();
	}

	/*
		Checks if the stack is empty. 
	*/
	public boolean isEmpty(){
		return top == null;
	}
}

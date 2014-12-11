package myList;

public class MyList 
{
	private static Node pool = null;
	
	private static class Node
	{
		Node next;
		Node prev;
		int item;
		
		final static int BLOCK_SIZE = 32;
		
		public static Node allocate ()
		{
			if (pool == null)
			{
				Node[] block = new Node[BLOCK_SIZE];
				for (int i = 0; i < BLOCK_SIZE; i++)
					block[i] = new Node(); 
				
				pool = block[0];
				for (int i = 0; i < BLOCK_SIZE; i++)
					block[i].next = block[i + 1];
				block[BLOCK_SIZE - 1].next = null;
			}
			
			Node v = pool;
			pool = v.next;
			
			return v;
		}
		
		public static Node allocate(int item, Node next, Node prev)
		{
			Node v = allocate();
			v.item = item;
			v.next = next;
			v.prev = prev;
			
			return v;
		}
		
		public void free ()
		{
			this.next = pool;
			pool = this;
		}
	};
	
	public static class Handle
	{
		Node myNode;
		Node myHead;
		
		public Handle (Node myNode, Node myHead)
		{
			this.myNode = myNode;
			this.myHead = myHead;
		}
		
		public Handle (Handle h)
		{
			this(h.myNode, h.myHead);
		}
		
		public Handle (MyList l)
		{
			this(l.header, l.header);
		}
		
		public Handle copy ()
		{
			return new Handle(this);
		}
		
		public int item ()
		{
			return (myNode != myHead)? myNode.item : null;
		}
		
		public boolean forward ()
		{
			myNode = myNode.next;
			return myNode != myHead;
		}
		
		public boolean backward ()
		{
			myNode = myNode.prev;
			return myNode != myHead;
		}
		
		public Handle next ()
		{
			return new Handle(myNode.next, myHead);
		}
		
		public Handle prev ()
		{
			return new Handle(myNode.prev, myHead);
		}
		
		public Handle insertAfter (int item)
		{
			Node v = Node.allocate(item, myNode.next, myNode);
			myNode.next.prev = v;
			myNode.next = v;
			return new Handle(v, myHead);
		}
		
		public Handle insertBefore (int item)
		{
			return prev().insertAfter(item);
		}
		
		public boolean equal (Handle h)
		{
			return myNode == h.myNode && myHead == h.myHead;
		}
		
		public int delete ()
		{
			myNode.next.prev = myNode.prev;
			myNode.prev.next = myNode.next;
			int item = myNode.item;
			myNode.free();
			return item;
		}
		
		public void eject ()
		{
			myNode.next.prev = myNode.prev;
			myNode.prev.next = myNode.next;
		}
		
		public void inject ()
		{
			myNode.next.prev = myNode;
			myNode.prev.next = myNode;
		}
		
	}
	
	Node header;
	
	public MyList ()
	{
		header = Node.allocate();
		header.next = header;
		header.prev = header;
	}
	
	public Handle handle()
	{
		return new Handle(this);
	}
	
	public MyList copy ()
	{
		MyList l = new MyList();
		for (Handle h = new Handle(this); h.forward(); )
		{
			Node v = Node.allocate(h.myNode.item, null, l.header.prev);
			l.header.prev.next = v;
			l.header.prev = v;
		}
		l.header.prev.next = l.header;
		return l;
	}
	
	public Handle push (int item)
	{
		return (new Handle(this)).insertAfter(item);
	}
	
	public int pop ()
	{
		return (new Handle(this).next()).delete();
	}
	
	public Handle put (int item)
	{
		return (new Handle(this)).insertBefore(item);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package myList;

public class List
{
    private static Node pool = null; // Pool of free nodes

    private static class Node // List node
    {
    	int  item;
    	Node next;
    	Node prev;

        final static int BLOCK_SIZE = 32; // Number of nodes in a block

        // allocate -- Allocate a node by taking one from the pool
        //
        public static Node allocate ()
        {
            // If the pool is empty, allocate a block of nodes and put them
            // into the pool
            //
            if (pool == null)
            {
                // Allocate a block of nodes
            	//
            	Node[] block = new Node[BLOCK_SIZE];
                for (int i = 0; i < BLOCK_SIZE; i++)
                	block[i] = new Node(); // (Darn you, Java!)

                // Link the nodes in the block into a pool
                //
                pool = block[0];
                for (int i = 0; i < BLOCK_SIZE - 1; i++)
                	block[i].next = block[i + 1];
                block[BLOCK_SIZE - 1].next = null;
            }

            // Take a node off the pool
            //
            Node v = pool;
            pool = v.next;

            return v;
        }
        public static Node allocate (int item, Node next, Node prev)
        {
        	Node v = allocate();

        	// Initialize the node fields
            //
        	v.item = item;
        	v.next = next;
        	v.prev = prev;

        	return v;
        }

        // free -- Free a node by putting it into the pool
        //
        public void free ()
        {
        	this.next = pool;
        	pool = this;
        }
    };

	public static class Handle // Pointer to a list node
    {
		private Node myNode; // Node pointed at 
		private Node myHead; // Header node of the list containing myNode

        // Handle constructors
        //
        public Handle (Node myNode, Node myHead)
        {
        	this.myNode = myNode;
        	this.myHead = myHead;
        }
        public Handle (Handle h)
        {
        	this(h.myNode, h.myHead);
        }
        public Handle (List l)
        {
        	this(l.header, l.header);
        }

        // copy -- Copy a handle
        //
        public Handle copy ()
        {
        	return new Handle(this);
        }

        // item -- Return the item in the node pointed at by a handle
        //
        public int item ()
        {
//	    	return (myNode != myHead) ? myNode.item : null;
        	return myNode.item;
        }

        // forward -- Move the handle to the next node; returns true iff
        //            this does not fall off the list
        //
        public boolean forward ()
        {
        	myNode = myNode.next;
        	return myNode != myHead;
        }

        // backward -- Move the handle to the previous node; returns true iff
        //             this does not fall off the list
        //
        public boolean backward ()
        {
        	myNode = myNode.prev;
        	return myNode != myHead;
        }

        // next -- Return a new handle that points to the next node
        //
        public Handle next ()
        {
        	return new Handle(myNode.next, myHead);
        }

        // prev -- Return a new handle that points to the previous node
        //
        public Handle prev ()
        {
        	return new Handle(myNode.prev, myHead);
        }

        // insertAfter -- Insert an item after the handle
        //
        public Handle insertAfter (int item)
        {
        	Node v = Node.allocate(item, myNode.next, myNode);
        	myNode.next.prev = v;
        	myNode.next = v;
        	return new Handle(v, myHead);
        }

        // insertBefore -- Insert an item before the handle
        //
        public Handle insertBefore (int item)
        {
        	return prev().insertAfter(item);
        }

        // equal -- Are handles equal?
        //
        public boolean equal (Handle h)
        {
        	return myNode == h.myNode && myHead == h.myHead;
        }

        // delete -- Remove from the list the element pointed at by the handle
        //
        public int delete ()
        {
        	myNode.next.prev = myNode.prev;
        	myNode.prev.next = myNode.next;
        	int item = myNode.item;
        	myNode.free();
        	return item;
        }

        // eject -- Unlink from the list the node pointed at by the handle
        //
        // This does not free the ejected node.
        //
        public void eject ()
        {
        	myNode.next.prev = myNode.prev;
        	myNode.prev.next = myNode.next;
        }

        // inject -- Link back into the list an ejected node
        //
        // An eject followed by an inject restores the prior state of the list
        // if the series of eject and inject operations are performed in a
        // stack-like manner.
        //
        public void inject ()
        {
        	myNode.next.prev = myNode;
        	myNode.prev.next = myNode;
        }
    }

    Node header; // Header node for the list

    // List constructor
    //
    public List ()
    {
    	header = Node.allocate();
        header.next = header;
        header.prev = header;
    }

    // handle -- Return a new handle for the list
    //
    // The handle is initialized ready for iteration over the list.
    //
    public Handle handle ()
    {
    	return new Handle(this);
    }

    // copy -- Shallow list copy
    //

    public List copy ()
    {
    	List l = new List();
    	for (List.Handle h = this.handle(); h.forward(); )
    	{
    		l.header.prev.next = Node.allocate(h.myNode.item, l.header, l.header.prev);
    		l.header.prev = l.header.prev.next;
    	}
    	return l;
    } // implement this

    // push -- Push an element onto the top of a stack
    //
    public Handle push (int item)
    {
    	return (new Handle(this)).insertAfter(item);
    }

    // pop -- Pop the element off the top of a stack
    //
    public int pop ()
    {
    	return (new Handle(header.next, header)).delete();
    }

    // put -- Put an element onto the rear of a queue
    //
    public Handle put (int item)
    {
    	return (new Handle(this)).insertBefore(item);
    }

    // get -- Get the element off the front of a queue
    //
    public int get ()
    {
    	return pop();
    }

    // pull -- Remove the element at the rear of a deque
    //
    public int pull ()
    {
    	return (new Handle(header.prev, header)).delete();
    } // implement this

    // top -- Return the item at the top of a stack
    //
    public int top ()
    {
    	return this.header.next.item;
    } // implement this

    // front -- Return the item at the front of a queue
    //
    public int front ()
    { 
    	return top();
    }

    // rear -- Return the item at the rear of a queue
    //
    public int rear ()
    {
    	return this.header.prev.item;
    } // implement this
    
    // concatenate -- Destructively concatenate in O(1) time
    //
    // Concatenate places all the elements of the list argument after the
    // elements in this list, makes the list argument empty, and returns
    // this list.
    //
    public List concatenate (List l)
    {
    	this.header.prev.next = l.header.next;
    	l.header.next.prev = this.header.prev;
    	l.header.prev.next = this.header;
    	this.header.prev = l.header.prev;
    	l.header.next = l.header;
    	l.header.prev = l.header;
    	return this;
    } // implement this
    
    // splitBefore -- Split a list before a given element, destructively
    //
    // Returns a new list containing all elements after the handle, including
    // the handle element.
    //
    public List splitBefore (Handle h)
    {
    	List l = new List();
    	Handle h1 = h.prev();
    	h.myHead = l.header;
    	h.myNode.prev = l.header;
    	l.header.next = h.myNode;
    	this.header.prev.next = l.header;
    	l.header.prev = this.header.prev;
    	h1.myNode.next = this.header;
    	this.header.prev = h1.myNode;
    	return l;
    } // implement this

    // splitAfter -- Split a list after a given element, destructively
    //
    // Returns a new list containing all elements after the handle.
    //
    public List splitAfter (Handle h)
    {
    	List l = new List();
    	Handle h2 = h.next();
    	h2.myHead = l.header;
    	h2.myNode.prev = l.header;
    	l.header.next = h2.myNode;
    	h.myNode.next = this.header;
    	this.header.prev.next = l.header;
    	l.header.prev = this.header.prev;
    	this.header.prev = h.myNode;
    	return l;
    } // implement this

    // empty -- Is the list empty?
    //
    public boolean empty ()
    {
    	return this.header.next == this.header && this.header.prev == this.header;
    } // implement this
    
    // size -- Count the number of nodes on the list
    //
    // This is a linear-time operation.
    //
    public long size ()
    {
    	long i = 0;
    	for (Handle h = new Handle(this); h.forward(); )
    		i++;
    	return i;
    }
    // reverse -- Reverse the elements on the list
    //
    public void reverse ()
    {
    	if (!this.empty())
    	{
    		Node v = Node.allocate();
    		v = this.header.next;
    		this.header.next = this.header.prev;
    		this.header.prev = v;
    		for (List.Handle h = this.handle(); h.backward(); )
    		{
    			v = h.myNode.next;
    			h.myNode.next = h.myNode.prev;
    			h.myNode.prev = v;
    		}
    	}
    } // implement this

    // clear -- Make a list empty in O(1) time
    //
    // Clear places all the list nodes in the pool.
    //
    public void clear ()
    {
    	if (!this.empty())
    	{
        	this.header.prev.next = pool;
        	pool = this.header.next;
        	this.header.next = header;
        	this.header.prev = header;
    	}
    } // implement this
}
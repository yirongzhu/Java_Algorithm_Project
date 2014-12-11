package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import myTree.Tree;

import org.junit.Test;

/**
 * Test that tests adding 100,000 elements, might take a while to complete
 * 
 * @author Jeremy Mowery
 *
 */
public class MasterTest {

	@Test
	public void testForward() {
		Tree A = new Tree();
		Random random = new Random();
		int min = -1;
		int max = -1;
		int oldMin = -1;
		int oldMax = -1;
		for (int i = 1; i <= 100000; i += 1) {
			int j = random.nextInt(i);
			A.insert(j, 2 * j);
			A.insert(j, 2 * j);
			assertEquals(A.find(j), j * 2);
			if (j > max) {
				assertTrue(j == A.max());
				oldMax = max;
				max = j;
				if (i > 2 && oldMax != -1) {
					assertTrue(A.prev(max) == oldMax);
					assertTrue(A.next(oldMax) == max);
					assertTrue(A.next(max) == null);
				}
			} else if (j < min) {
				assertTrue(j == A.min());
				oldMin = min;
				min = j;
				if (i > 2 && oldMin != -1) {
					assertTrue(A.next(min) == oldMin);
					assertTrue(A.prev(oldMin) == min);
					assertTrue(A.prev(min) == null);
				}
			}
			assertTrue(A.valid());
		}

		// Get the min from the tree and continuously call next until we get the
		// max
		min = A.min();
		max = A.max();
		int size = A.size();
		int index = 0;

		Integer next = A.next(min);
		int old = 0;
		while (next < max && index <= size) {
			old = next;
			next = A.next(next);
			index++;
			if (next < old) {
				fail("Next is not returning a larger value!");
			}
		}
		if (index > size || next != max) {
			fail("Did not get to the max");
		}
		Integer prev = A.prev(max);
		index = 0;
		while (prev > min && index <= size) {
			old = prev;
			prev = A.prev(prev);
			index++;
			if (prev > old) {
				fail("Prev is not returning a smaller element");
			}
		}
		if (index > size || prev != min) {
			fail("Did not get to the min");
		}

		System.out.println("****************************************");
		System.out.println("Remember to make valid and dump private again!");
		System.out.println("****************************************");
	}

	@Test
	public void testReverse() {
		Tree A = new Tree();
		Random random = new Random();
		int min = -1;
		int max = -1;
		int oldMin = -1;
		int oldMax = -1;
		for (int i = 100000; i > 0; i -= 1) {
			int j = random.nextInt(i);
			A.insert(j, 2 * j);
			A.insert(j, 2 * j);
			assertEquals(A.find(j), j * 2);
			if (j > max) {
				assertTrue(j == A.max());
				oldMax = max;
				max = j;
				if (i > 2 && oldMax != -1) {
					assertTrue(A.prev(max) == oldMax);
					assertTrue(A.next(oldMax) == max);
					assertTrue(A.next(max) == null);
				}
			} else if (j < min) {
				assertTrue(j == A.min());
				oldMin = min;
				min = j;
				if (i > 2 && oldMin != -1) {
					assertTrue(A.next(min) == oldMin);
					assertTrue(A.prev(oldMin) == min);
					assertTrue(A.prev(min) == null);
				}
			}
			assertTrue(A.valid());
		}

		// Get the min from the tree and continuously call next until we get the
		// max
		min = A.min();
		max = A.max();
		int size = A.size();
		int index = 0;

		Integer next = A.next(min);
		int old = 0;
		while (next < max && index <= size) {
			old = next;
			next = A.next(next);
			index++;
			if (next < old) {
				fail("Next is not returning a larger value!");
			}
		}
		if (index > size || next != max) {
			fail("Did not get to the max");
		}
		Integer prev = A.prev(max);
		index = 0;
		while (prev > min && index <= size) {
			old = prev;
			prev = A.prev(prev);
			index++;
			if (prev > old) {
				fail("Prev is not returning a smaller element");
			}
		}
		if (index > size || prev != min) {
			fail("Did not get to the min");
		}

		System.out.println("****************************************");
		System.out.println("Remember to make valid and dump private again!");
		System.out.println("****************************************");
	}

	@Test
	public void testMixForward() {
		Tree A = new Tree();
		Random random = new Random();
		int min = -1;
		int max = -1;
		int oldMin = -1;
		int oldMax = -1;
		for (int i = -100000; i <= 100000; i += 1) {
			int j = random.nextInt(Math.abs(i) + 1);
			if (i < 0) {
				j = -j;
			}
			A.insert(j, 2 * j);
			A.insert(j, 2 * j);
			assertEquals(A.find(j), j * 2);
			if (j > max) {
				assertTrue(j == A.max());
				oldMax = max;
				max = j;
				if (i > 2 && oldMax != -1) {
					assertTrue(A.prev(max) == oldMax);
					assertTrue(A.next(oldMax) == max);
					assertTrue(A.next(max) == null);
				}
			} else if (j < min) {
				assertTrue(j == A.min());
				oldMin = min;
				min = j;
				if (i > 2 && oldMin != -1) {
					assertTrue(A.next(min) == oldMin);
					assertTrue(A.prev(oldMin) == min);
					assertTrue(A.prev(min) == null);
				}
			}
			assertTrue(A.valid());
		}

		// Get the min from the tree and continuously call next until we get the
		// max
		min = A.min();
		max = A.max();
		int size = A.size();
		int index = 0;

		Integer next = A.next(min);
		int old = 0;
		while (next < max && index <= size) {
			old = next;
			next = A.next(next);
			index++;
			if (next < old) {
				fail("Next is not returning a larger value!");
			}
		}
		if (index > size || next != max) {
			fail("Did not get to the max");
		}
		Integer prev = A.prev(max);
		index = 0;
		while (prev > min && index <= size) {
			old = prev;
			prev = A.prev(prev);
			index++;
			if (prev > old) {
				fail("Prev is not returning a smaller element");
			}
		}
		if (index > size || prev != min) {
			fail("Did not get to the min");
		}

		System.out.println("****************************************");
		System.out.println("Remember to make valid and dump private again!");
		System.out.println("****************************************");
	}

	@Test
	public void testMixBackward() {
		Tree A = new Tree();
		Random random = new Random();
		int min = -1;
		int max = -1;
		int oldMin = -1;
		int oldMax = -1;
		for (int i = 100000; i >= -100000; i -= 1) {
			int j = random.nextInt(Math.abs(i) + 1);
			if (i < 0) {
				j = -j;
			}
			A.insert(j, 2 * j);
			A.insert(j, 2 * j);
			assertEquals(A.find(j), j * 2);
			if (j > max) {
				assertTrue(j == A.max());
				oldMax = max;
				max = j;
				if (i > 2 && oldMax != -1) {
					assertTrue(A.prev(max) == oldMax);
					assertTrue(A.next(oldMax) == max);
					assertTrue(A.next(max) == null);
				}
			} else if (j < min) {
				assertTrue(j == A.min());
				oldMin = min;
				min = j;
				if (i > 2 && oldMin != -1) {
					assertTrue(A.next(min) == oldMin);
					assertTrue(A.prev(oldMin) == min);
					assertTrue(A.prev(min) == null);
				}
			}
			assertTrue(A.valid());
		}

		// Get the min from the tree and continuously call next until we get the
		// max
		min = A.min();
		max = A.max();
		int size = A.size();
		int index = 0;

		Integer next = A.next(min);
		int old = 0;
		while (next < max && index <= size) {
			old = next;
			next = A.next(next);
			index++;
			if (next < old) {
				fail("Next is not returning a larger value!");
			}
		}
		if (index > size || next != max) {
			fail("Did not get to the max");
		}
		Integer prev = A.prev(max);
		index = 0;
		while (prev > min && index <= size) {
			old = prev;
			prev = A.prev(prev);
			index++;
			if (prev > old) {
				fail("Prev is not returning a smaller element");
			}
		}
		if (index > size || prev != min) {
			fail("Did not get to the min");
		}

		System.out.println("****************************************");
		System.out.println("Remember to make valid and dump private again!");
		System.out.println("****************************************");
	}
}
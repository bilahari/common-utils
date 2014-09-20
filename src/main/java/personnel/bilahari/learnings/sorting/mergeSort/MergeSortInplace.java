/**
 * 
 */
package personnel.bilahari.learnings.sorting.mergeSort;

import java.util.Arrays;


/**
 * @author bilahari.th
 *
 */
public class MergeSortInplace {
	
	private int[] input;

	public int[] mergeSort(int[] a){
		input = a;
		merge(0, input.length-1);
	}
	
	private int[] mergeSort(int l,int h){
		if(l < h){
			int midIndex = 0;
			if(a.length%2 == 0)		midIndex = a.length/2-1;
			else					midIndex = a.length/2;
			int[] leftHalf = new int[midIndex+1]; 
			int[] rightHalf = new int[a.length-midIndex-1];
			System.arraycopy(a, 0, leftHalf, 0, midIndex+1);
			System.arraycopy(a, midIndex+1, rightHalf, 0, a.length-midIndex-1);
			return merge(mergeSort(leftHalf),mergeSort(rightHalf));
		}
	}

	private static int[] merge(int[] a,int[] b){
		int[] c = new int[a.length+b.length];
		int i=0,j=0,k=0;
		while(i<a.length && j<b.length){
			if(a[i]<b[j]){
				c[k++] = a[i++];
			}else{
				c[k++] = b[j++];
			}
		}
		while(j<b.length){
			c[k++] = b[j++];
		}
		while(i<a.length){
			c[k++] = a[i++];
		}
		return c;
	}

	public static void main(String[] args){
		int[] a = new int[]{50,25,75,12,86,45,98,46};
		System.out.println(Arrays.toString(a));
		int[] b = new int[a.length]; 
		b = mergeSort(a);
		System.out.println(Arrays.toString(b));
	}
}
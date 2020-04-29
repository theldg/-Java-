package com.ldg.selectionsort;



public class Main {

	public static void main(String[] args) {
		Integer[] array = new Integer[] { 16, 15, 14, 17, 5 };
		for (int end = array.length; end > 1; end--) {
			int max = 0;
			for (int begin = 1; begin < end; begin++) {
				//稳定性
				if (array[begin] >= array[max]) {
					max = begin;
				}
			}
			//交换位置
			int temp = array[end - 1];
			array[end - 1] = array[max];
			array[max] = temp;
		}

		for (int i : array) {
			System.out.print(i+"_");
		}
	}

}

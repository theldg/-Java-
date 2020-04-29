package com.ldg.BubbleSort;

public class Main {

	public static void main(String[] args) {
		bubbleSort03();

	}

	// 冒泡排序优化2:尾部已经排好顺序的数组可以不用每次都比较尾部
	private static void bubbleSort03() {
		Integer[] array = new Integer[] {3,2,1,4,5,6,7,8,9 };
		
		for (int end = array.length; end > 1; end--) {
			
			int flag = 0;
			for (int begin = 1; begin < end; begin++) {

				if (array[begin] < array[begin - 1]) {
					int temp = array[begin];
					array[begin] = array[begin - 1];
					array[begin - 1] = temp;
					//记录最后一次交换的索引 注意一下是否要+1
					flag = begin+1 ;
				}
			}
			end = flag;

		}

		for (Integer i : array) {
			System.out.print(i + "_");
		}
	}

	// 冒泡排序优化1:可以检测原来是否有序的。
	private static void bubbleSort02() {
		Integer[] array = new Integer[] { 0, 4551, 15, 78, 1, 2, 3, 6, 4 };
		for (int end = array.length; end > 1; end--) {
			// 是否停止
			boolean flag = false;

			for (int begin = 1; begin < end; begin++) {

				if (array[begin] < array[begin - 1]) {
					int temp = array[begin];
					array[begin] = array[begin - 1];
					array[begin - 1] = temp;
					flag = true;

				}
			}
			// 如果没变则说明数组一开始就是有序的
			if (flag)
				break;
		}

		for (Integer i : array) {
			System.out.print(i + "_");
		}
	}

	// 冒泡排序原版
	private static void bubbleSort01() {
		Integer[] array = new Integer[] { 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

		for (int end = array.length; end > 1; end--) {
			for (int begin = 1; begin < end; begin++) {
				// 比较
				if (array[begin] < array[begin - 1]) {
					int temp = array[begin];
					array[begin] = array[begin - 1];
					array[begin - 1] = temp;
				}
			}
		}
		// 3 2 1

		for (Integer i : array) {
			System.out.print(i + "_");
		}
	}

}

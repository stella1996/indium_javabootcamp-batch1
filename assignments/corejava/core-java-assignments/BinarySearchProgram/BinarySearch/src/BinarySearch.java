class BinarySearch {

	public static void binarySearch(int number[], int firstindex, int lastindex, int searchvalue) {
		int middleelement = (firstindex + lastindex) / 2;
		while (firstindex <= lastindex) {
			if (number[middleelement] < searchvalue) {
				firstindex = middleelement + 1;
			} else if (number[middleelement] == searchvalue) {
				System.out.println("Element is found at index: " + middleelement);
				break;
			} else {
				lastindex = middleelement - 1;
			}
			middleelement = (firstindex + lastindex) / 2;
		}
		if (firstindex > lastindex) {
			System.out.println("Element is not found!");
		}
	}

	public static void main(String args[]) {
		int number[] = { 10, 20, 30, 40, 50 };
		int searchvalue = 20;
		int lastindex = number.length - 1;
		binarySearch(number, 0, lastindex, searchvalue);
	}
}
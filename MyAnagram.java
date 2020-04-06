public class MyAnagram {
	public static void main(String []args){
        ApuAnagram myAnagram = new ApuAnagram();
        // Test # 1 : Pass
		if(myAnagram.areAnagrams("cineMa", "iceMan")){
            System.out.println("Anagram Test {cineMa, iceMan} - Pass");
        }else{
            System.out.println("Anagram Test {cineMa, iceMan} - Fail");
        }
		// Test # 2 : Pass
		if(myAnagram.areAnagrams("cinema", "iceman")){
            System.out.println("Anagram Test {cinema, iceman} - Pass");
        }else{
            System.out.println("Anagram Test {cinema, iceman} - Fail");
        }
		// Test # 3 : Fail
		if(myAnagram.areAnagrams("cinema", "icemann")){
            System.out.println("Anagram Test {cinema, icemann} - Pass");
        }else{
            System.out.println("Anagram Test {cinema, icemann} - Fail");
        }
		// Test # 4 : Fail
		if(myAnagram.areAnagrams("cinema", "icgman")){
            System.out.println("Anagram Test {cinema, icgman} - Pass");
        }else{
            System.out.println("Anagram Test {cinema, icgman} - Fail");
        }
    }
}
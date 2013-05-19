package java.lejoslang;

public interface CharSequence
{
	char charAt(int i);
	int length();
	CharSequence subSequence(int start, int end);
	java.lang.String toString();
}

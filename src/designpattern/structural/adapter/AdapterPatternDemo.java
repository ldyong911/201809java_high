package designpattern.structural.adapter;

public class AdapterPatternDemo {
	public static void main(String[] args) {
		AudioPlayer audioPlayer = new AudioPlayer();
		
		audioPlayer.play("mp3", "beyond the horizon.mp3");
		audioPlayer.play("mp4", "사랑과 영혼.mp4");
		audioPlayer.play("vlc", "mars.vlc");
		audioPlayer.play("avi", "재미있는 영화.avi");
	}
}
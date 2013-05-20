package com.se1by.ETM.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;

public class Networker {
	
	Socket socket;
	BufferedWriter bw;
	BufferedReader br;
	String toThe1337h4x0rs = "I know, you can fuck my highscores up. But this is a LD game, please keep the spirit ;)";
	
	public Networker(){
		try {
			socket = new Socket("109.230.252.20", 13333);
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error initializing socket and BufferedWriter/Reader!");
			e.printStackTrace();
		}
	}
	
	public boolean addScore(String user, int score){
		if(score < 0){
			return false;
		}
		try {
			String toSend = "Entry:" + user + ":" + score + "\n";
			bw.write(toSend);
			bw.flush();
			String s;
			while((s = br.readLine()) != null){
				if(s.startsWith("Done")){
					return true;
				}
				else if(s.startsWith("Error")){
					System.out.println(s);
					return false;
				}
			}
		} catch (IOException e) {
			System.out.println("Error initializing the BufferedWriter/BufferedReader!");
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public int getScore(String user){
		try {
			bw.write("Request:" + user + "\n");
			bw.flush();
			String s;
			while((s = br.readLine()) != null){
				if(s.startsWith(user)){
					System.out.println(s);
					return Integer.parseInt(s.split(":")[1]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error initializing the BufferedWriter/BufferedReader!");
			e.printStackTrace();
		}
		return 0;
	}
	
	public LinkedHashMap<String, Integer> getTopScore(){
		LinkedHashMap<String, Integer> results = null;
		try {
			bw.write("RequestTop\n");
			bw.flush();
			String s;
			s = br.readLine();
			results = new LinkedHashMap<String, Integer>();
			for(String split : s.split(";")){
				results.put(split.split(":")[0], Integer.parseInt(split.split(":")[1]));
			}
		} catch (IOException e) {
			System.out.println("Error initializing the BufferedWriter/BufferedReader!");
			e.printStackTrace();
		}
		return results;
	}
	
	public boolean end(){
		boolean result = false;
		try {
			bw.write("END\n");
			bw.flush();
			String s;
			while((s = br.readLine()) != null){
				if(s.startsWith("END")){
					result = true;
				}
			}
		} catch (IOException e) {
			System.out.println("Error initializing the BufferedWriter/BufferedReader!");
			e.printStackTrace();
		} finally{
			try{
				bw.close();
				br.close();
				socket.close();
			} catch(IOException ioe){
				System.out.println("Error closing BufferedWriter/BufferedReader!");
				ioe.printStackTrace();
			}
		}
		return result;
	}
}

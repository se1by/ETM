package com.se1by.ETM.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Networker {
	
	Socket socket;
	
	public Networker(){
		try {
			socket = new Socket("109.230.252.20", 13333);
		} catch (IOException e) {
			System.out.println("Error initializing the socket!");
			e.printStackTrace();
		}
	}
	
	public boolean addScore(String user, int score){
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw.write("Entry:" + user + ":" + score);
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
		} finally{
			try{
				bw.close();
				br.close();
			} catch(IOException ioe){
				System.out.println("Error closing BufferedWriter/BufferedReader!");
				ioe.printStackTrace();
			}
		}
		return false;
	}
	
	public int getScore(String user){
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw.write("Request:" + user);
			bw.flush();
			String s;
			while((s = br.readLine()) != null){
				if(s.startsWith(user)){
					return Integer.parseInt(s.split(":")[1]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error initializing the BufferedWriter/BufferedReader!");
			e.printStackTrace();
		} finally{
			try{
				bw.close();
				br.close();
			} catch(IOException ioe){
				System.out.println("Error closing BufferedWriter/BufferedReader!");
				ioe.printStackTrace();
			}
		}
		return 0;
	}
	
	public boolean end(){
		BufferedWriter bw = null;
		BufferedReader br = null;
		boolean result = false;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
			} catch(IOException ioe){
				System.out.println("Error closing BufferedWriter/BufferedReader!");
				ioe.printStackTrace();
			}
		}
		return result;
	}

}

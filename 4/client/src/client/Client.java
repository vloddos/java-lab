package client;

import common.Query;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.net.*;

//Пример реализации клиента, отправляющего запросы на сервер:
public class Client {

	public static void main(String[] args) throws IOException {
		System.out.println("User ID: ");
		int uid = Integer.parseInt(System.console().readLine());
		while (true) {
			String str = System.console().readLine();
			//В данном конкретном случае, для отправки каждого запроса на сервер создается новое подключение:
			try (Socket soc = new Socket("localhost", 9027)) {
				try (
					ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
					ObjectInputStream src = new ObjectInputStream(soc.getInputStream())
				) {
					//out.writeObject(new Query(uid, str));	//Отправляем запрос на сервер;
					Query inf = (Query) src.readObject();	//Получаем ответ;
					//System.out.println(inf.text);
				}
			}
			catch (Exception err) {
				System.out.println("ERROR: " + err);
				break;
			}
		}
	}

}

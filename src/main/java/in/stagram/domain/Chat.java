package in.stagram.domain;

import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Chat implements Comparator<Chat>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "senduser")
	User send;
	
	@ManyToOne
	@JoinColumn(name = "receiveuser")
	User receive;
	
	String content;
	Timestamp writetime;

	@Override
	public int compare(Chat c1, Chat c2) {
		long l1 = c1.getWritetime().getTime();
		long l2 = c2.getWritetime().getTime();
		
		if(l1>l2)
			return 1;
		else
			return -1;
	}
}

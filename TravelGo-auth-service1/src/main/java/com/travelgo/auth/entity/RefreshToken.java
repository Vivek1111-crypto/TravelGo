package com.travelgo.auth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String token;

	    @OneToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    private LocalDateTime expiryDate;

	    public boolean isRevoked() {
			return revoked;
		}

		public void setRevoked(boolean revoked) {
			this.revoked = revoked;
		}

		private boolean revoked = false;
		public RefreshToken() {
			super();
			// TODO Auto-generated constructor stub
		}

		public RefreshToken(Long id, String token, User user, LocalDateTime expiryDate,boolean revoked) {
			super();
			this.id = id;
			this.token = token;
			this.user = user;
			this.expiryDate = expiryDate;
			this.revoked=revoked;
			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public LocalDateTime getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(LocalDateTime expiryDate) {
			this.expiryDate = expiryDate;
		}
}

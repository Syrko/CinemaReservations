CREATE TABLE Cinema (
	cinemaID VARCHAR(255) NOT NULL,
	cinemaIs3D BOOLEAN NOT NULL,
	cinemaNumberOfSeats integer NOT NULL,
	CONSTRAINT Cinema_pk PRIMARY KEY (cinemaID)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Film (
	filmID VARCHAR(255) NOT NULL,
	filmTitle VARCHAR(255) NOT NULL,
	filmCategory VARCHAR(255) NOT NULL,
	filmDescription TEXT NOT NULL,
	CONSTRAINT Film_pk PRIMARY KEY (filmID)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Provoli (
	provoliID VARCHAR(255) NOT NULL,
	provoliFilm VARCHAR(255) NOT NULL,
	provoliCinema VARCHAR(255) NOT NULL,
	provoliStartDate DATE NOT NULL,
	provoliEndDate DATE NOT NULL,
	provoliNumberOfReservations integer NOT NULL,
	provoliIsAvailable BOOLEAN NOT NULL,
	CONSTRAINT Provoli_pk PRIMARY KEY (provoliID)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Admin (
	username VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	CONSTRAINT Admin_pk PRIMARY KEY (username)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Customer (
	name VARCHAR NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	reservation VARCHAR(255) NOT NULL,
	CONSTRAINT Customer_pk PRIMARY KEY (username)
) WITH (
  OIDS=FALSE
);



CREATE TABLE ContentAdmin (
	name VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	CONSTRAINT ContentAdmin_pk PRIMARY KEY (username)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Reservation (
	reservationID VARCHAR(255) NOT NULL,
	provoli VARCHAR(255) NOT NULL,
	numberOfSeats integer NOT NULL,
	CONSTRAINT Reservation_pk PRIMARY KEY (reservationID)
) WITH (
  OIDS=FALSE
);





ALTER TABLE Provoli ADD CONSTRAINT Provoli_fk0 FOREIGN KEY (provoliFilm) REFERENCES Film(filmID);
ALTER TABLE Provoli ADD CONSTRAINT Provoli_fk1 FOREIGN KEY (provoliCinema) REFERENCES Cinema(cinemaID);




ALTER TABLE Reservation ADD CONSTRAINT Reservation_fk0 FOREIGN KEY (provoli) REFERENCES Provoli(provoliID);

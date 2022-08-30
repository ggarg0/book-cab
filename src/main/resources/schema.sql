/*
 * *
 *  * The MIT License (MIT)
 *  * <p>
 *  * Copyright (c) 2022
 *  * <p>
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  * <p>
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  * <p>
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

DROP TABLE IF EXISTS USER_ACCOUNT;
CREATE TABLE USER_ACCOUNT (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              first_name VARCHAR(250) NOT NULL,
                              last_name VARCHAR(250) NOT NULL,
                              user_name LONG NOT NULL,
                              email VARCHAR(250) DEFAULT NULL,
                              wallet_pin VARCHAR(500),
                              wallet_balance DOUBLE,
                              wallet_balance_onhold DOUBLE
);
ALTER TABLE USER_ACCOUNT ADD CONSTRAINT email_uq UNIQUE(email);
ALTER TABLE USER_ACCOUNT ADD CONSTRAINT user_name_uq UNIQUE(user_name);

DROP TABLE IF EXISTS CAB_DETAILS;
CREATE TABLE CAB_DETAILS (
      id INT AUTO_INCREMENT  PRIMARY KEY,
      cab_name VARCHAR(250) NOT NULL,
      cab_number VARCHAR(250) NOT NULL,
      driver_name VARCHAR(250) NOT NULL,
      rate DOUBLE NOT NULL,
      status VARCHAR(100) NOT NULL
);
ALTER TABLE CAB_DETAILS ADD CONSTRAINT cab_number_uq UNIQUE(cab_number);

DROP TABLE IF EXISTS TRIP_DETAILS;
CREATE TABLE TRIP_DETAILS (
      id INT AUTO_INCREMENT  PRIMARY KEY,
      location_from VARCHAR(250) NOT NULL,
      location_to VARCHAR(250) NOT NULL,
      fare DOUBLE NOT NULL,
      user_name VARCHAR(250) NOT NULL,
      cab_number VARCHAR(250) NOT NULL,
      status VARCHAR(250) NOT NULL
);


DROP TABLE IF EXISTS LOCATION_DETAILS;
CREATE TABLE LOCATION_DETAILS (
      id INT AUTO_INCREMENT  PRIMARY KEY,
      location_name VARCHAR(250) NOT NULL,
      location_sequence INT NOT NULL,
      pickup_count INT default 0,
      drop_count INT default 0,
      tariff DOUBLE
);
ALTER TABLE LOCATION_DETAILS ADD CONSTRAINT location_name_uq UNIQUE(location_name);

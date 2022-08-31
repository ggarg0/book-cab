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

INSERT INTO RIDER_ACCOUNT (first_name, last_name, user_name, email, wallet_pin, wallet_balance, wallet_balance_onhold)
VALUES ('Adam', 'Sandler', '1234567890', 'adam@hollywood.com', '$2a$10$AVI6e8rkyn.9Y93Yrbu9/.YM8plIBGZ33PUQy57U1fXw34uXT9mNC', 800, 0),
	   ('Brad', 'Pitt', '2345678901', 'brad@hollywood.com', '$2a$10$BkDnsFV/DA1.fIZ0l6BkEOcWxYMc3313nN2uD0YYNLKkwx6cgY/y.', 500, 0),
	   ('Mark', 'Waugh', '3456789012', 'mark@cricket.com', '$2a$10$2qcBY05Bo2W9VrlX7HUm1ONdc0OwTu5qF7382DOFlB3tnMydEpdl2', 1500, 0),
       ('Chris', 'Gayle', '4567890123', 'chris@cricket.com', '$2a$10$k2QUCfZf.t7dAHC3OvwdyO01yl5sNHhewaGBwyZRuCx/3LuzTQpPq', 1230, 0);

INSERT INTO CAB_DETAILS (cab_name, cab_number, driver_name, rate, status)
VALUES ('SWIFT', 'CAR1', 'Arun', 10, 'Available'),
       ('DZIRE', 'CAR2', 'Jai', 12, 'Available'),
       ('INNOVA', 'CAR3', 'Veeru', 15, 'Available'),
       ('ALTO', 'CAR4', 'Thakur', 8, 'Available'),
       ('SWIFT', 'CAR5', 'Ajay', 10, 'Available'),
       ('SWIFT', 'CAR6', 'Vijay', 10, 'Available'),
       ('SWIFT', 'CAR7', 'Sanjay', 10, 'Available'),
       ('ALTO', 'CAR8', 'Manju', 8, 'Available');
            
INSERT INTO LOCATION_DETAILS (location_name, location_sequence, pickup_count, drop_count, distance)
VALUES ('A1', 1, 0, 0, 0.0),
       ('A2', 2, 0, 0, 6.5),
       ('A3', 3, 0, 0, 15.2),
       ('A4', 4, 0, 0, 22.8),
       ('A5', 5, 0, 0, 29.0),
       ('A6', 6, 0, 0, 37.5),
       ('A7', 7, 0, 0, 46.3),
       ('A8', 8, 0, 0, 55.8),
       ('A9', 9, 0, 0, 61.0);
       
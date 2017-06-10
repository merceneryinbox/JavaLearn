create schema if not exists dic;

create table if not exists word_define (word varchar(35) not null primary key, definition varchar(400) not null);

insert into word_define (word, definition) values ('sql','structured querying language');
insert into word_define (word, definition) values ('html','hypertext markup language');
insert into word_define (word, definition) values ('BIOS','Basic Input/Output System');
insert into word_define (word, definition) values ('bot','abbreviation of robot');
insert into word_define (word, definition) values ('CCC','Chaos Computer Club, A hacker-organisation in Hamburg');
insert into word_define (word, definition) values ('Gibson','Considered by most to be the "father" of cyberpunk');
insert into word_define (word, definition) values ('IRC','Internet Relay Chat');
insert into word_define (word, definition) values ('Java','An object-oriented programming language from Oracle that is platform independent');
insert into word_define (word, definition) values ('php','PHP: Hypertext Preprocessor');
insert into word_define (word, definition) values ('ICMP','- Internet Control Message Protocol');
insert into word_define (word, definition) values ('Gosling','James Arthur Gosling, OC (born May 19, 1955) is a Canadian computer scientist, best known as the creator of the Java');
insert into word_define (word, definition) values ('Assembler','program creates object code by translating combinations of mnemonics and syntax for operations and addressing modes into their numerical equivalents.');

select * from word_define;
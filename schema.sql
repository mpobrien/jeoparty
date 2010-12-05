CREATE TABLE category 
     (category_id integer not null primary key auto_increment,
     name varchar(256) not null);

 CREATE TABLE question
     (question_id integer not null primary key auto_increment,
      category_id integer not null,
      value integer not null default 0,
      question varchar(1024) not null,
      answer varchar(1024) not null,
    FOREIGN KEY (category_id) references category(category_id));

CREATE TABLE urls
    (url_id integer not null primary key auto_increment,
     question_id integer not null,
     url varchar(512) not null,
     FOREIGN KEY (question_id) references question (question_id));



select * from users;
select * from logins;
insert into logins values (1, '21232f297a57a5a743894a0e4a801fc3');

insert into users values ('Main', 'Administrator', '27/12/1995', '902382@p.lodz.pl', 'a', null, null, null, 'wl2948pl');
insert into users values ('John', 'Doe', '01/01/1992', 'doe@p.lodz.pl', 's', 'ComputerScience', 15, null, null);
insert into users values ('Good', 'Teacher', '05/07/1976', 'gteacher@p.lodz.pl', 't', null, null, 'B9-245', null);
insert into logins values (2, LOWER(CONVERT(VARCHAR(32), HashBytes('MD5', 'student'), 2)));
insert into logins values (3, LOWER(CONVERT(VARCHAR(32), HashBytes('MD5', 'teacher'), 2)));

go
create or alter trigger set_password on users 
after insert
as
 declare @userrole char
 select @userrole = role from inserted;
 declare @password varchar(10);
begin
	if @userrole = 's'
		set @password = 'student';
	
	else if @userrole = 't' 
		set @password = 'teacher';
	
	else if @userrole = 'a' 
		set @password = 'admin';
	
	print N'password = ' + @password;
	insert into logins values ((select id from inserted), 
					LOWER(CONVERT(VARCHAR(32), 
					HashBytes('MD5', @password), 
					2)));
end;

insert into users values ('John', 'Doe', '01/01/1992', 'doe@p.lodz.pl', 's', 'ComputerScience', 15, null, null);
insert into users values ('John', 'Doe', '01/01/1992', 'doe@p.lodz.pl', 's', 'ComputerScience', 15, null, null);
insert into users values ('Good', 'Teacher', '05/07/1976', 'gteacher@p.lodz.pl', 't', null, null, 'B9-245', null);
insert into users values ('Main', 'Administrator', '27/12/1995', '902382@p.lodz.pl', 'a', null, null, null, 'wl2948pl');
select * from logins;

select  * from users;

delete from users where id > 3;
delete from logins where user_id > 3;

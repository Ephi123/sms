INSERT INTO users(id,created_at,created_by, update_at, updated_by, account_non_expired,account_non_locked,email,enable,mfa,user_id,first_name,last_name)
VALUES(0,now(),0,now(),0,TRUE,TRUE,'system@gmail.com',TRUE,FALSE,'system_id','system','system');


insert into semesters(
semester,
is_current,
created_at,
created_by,
update_at,
updated_by,
reference_id
)values(1,true,now(),0,now(),0,'ref_id');


insert into days(
created_at,
created_by,
update_at,
updated_by,
reference_id,
day
)values(now(),0,now(),0,'day_ref_id','Monday');
insert into days(
created_at,
created_by,
update_at,
updated_by,
reference_id,
day
)values(now(),0,now(),0,'day_ref_id','Tusday');
insert into days(
created_at,
created_by,
update_at,
updated_by,
reference_id,
day
)values(now(),0,now(),0,'day_ref_id','Wensday');
insert into days(
created_at,
created_by,
update_at,
updated_by,
reference_id,
day
)values(now(),0,now(),0,'day_ref_id','Thrsday');
insert into days(
created_at,
created_by,
update_at,
updated_by,
reference_id,
day
)values(now(),0,now(),0,'day_ref_id','Frayday');

insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',1);

insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',2);
insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',3);
insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',4);
insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',5);
insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',6);
insert into periods(
created_at,
created_by,
update_at,
updated_by,
reference_id,
period
)values(now(),0,now(),0,'day_ref_id',7);

insert into penalties values('penalty',0.5,10);
insert into registrations(id) values('reg');
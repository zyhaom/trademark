-- 当t_presearch删除一条记录前 ，在t_searchhistory中也相应的删除记录
drop trigger if exists tri_t_presearch_d;

create trigger tri_t_presearch_d after delete on t_presearch
for each row
begin
	delete from t_searchhistory where recordId = old.recordId;
end;

-- ��t_presearchɾ��һ����¼ǰ ����t_searchhistory��Ҳ��Ӧ��ɾ����¼
drop trigger if exists tri_t_presearch_d;

create trigger tri_t_presearch_d after delete on t_presearch
for each row
begin
	delete from t_searchhistory where recordId = old.recordId;
end;

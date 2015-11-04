## 좋아요
***REQ***

```
/good/{article_no}
```

***RES***

실패 (로그인 세션이 없을때)

```
{
result : false
}
```

성공 (좋아요 설정)

```
{
result : true,
article_no : {article_no},
good : true
}
```

성공 (좋아요 해제)

```
{
result : true,
article_no : {article_no},
good : true
}

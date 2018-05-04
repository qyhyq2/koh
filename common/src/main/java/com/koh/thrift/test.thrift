namespace java com.koh.thrift

struct SubData{
    1:i8 id
    2:string name
}

struct Data{
  1:i8 id
  2:string name
  3:SubData subData
}

service testService{
    bool exists(1:string path)
    string echo(1:string path)
    Data getById(1:i8 id)
}
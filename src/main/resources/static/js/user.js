var mytable, $, table, layer, upload, form, layedit, element, laydate;
var mylayedit;

//刷新函数
function reload() {
    /* mytable.reload({
     page: {
     curr: 1 //重新从第 1 页开始
     },
     })*/
    $(".layui-laypage-btn")[0].click();	//刷新当前页
}

//精准查询函数
function find() {
    if ($('input[name="find"]').val() == "") {
        layer.msg('请输入查询数据');
    }
    else {
        table.reload('teacherId', {
            url: '/Teacher/Find.do'
            , where: {
                data: $('input[name="find"]').val(),
            } //设定异步数据接口的额外参数
            //,height: 300
        });
    }

}

//模糊查询函数
function like() {
    if ($('input[name="like"]').val() == "") {
        layer.msg('请输入模糊查询数据');
    }
    else {
        var index = layer.load(2, {time: 10 * 1000});
        table.reload('teacherId', {
            url: '/Teacher/Like.do'
            , where: {
                data: $('input[name="like"]').val(),
            } //设定异步数据接口的额外参数
            //,height: 300
        });
        layer.close(index);
    }

}

//增加函数
function add() {
    var html = document.getElementById('addDOM_template').innerHTML;
    var index = layer.open({
        type: 1,
        title: '添加页面',
        maxmin: true, //开启最大化最小化按钮
        area: ['450px', '500px'],
        content: html,
        success: function (layero, index) {
            //日期
            laydate.render({
                elem:'#creationDate_add'
            });
            laydate.render({
                elem:'#lastUpdateDate_add'
            });
        },
        cancel: function (index, layero) {
            reload();
        },
    });
    var temp = 1;
    timer = setInterval(function (e) {
        if (temp < 100) {
            temp += temp;
            element.progress('progressdemo', temp + '%');
        } else {
            element.progress('progressdemo', 100 + '%');
            clearInterval(timer);
        }
    }, 100);
}

//批量删除
function moredelete() {
    var checkStatus = table.checkStatus('userId');
    var ids = new Array();
    if (checkStatus.data.length > 0) {
        for (j = 0, len = checkStatus.data.length; j < len; j++) {
            console.log(checkStatus.data[j].userId);
            ids.push(checkStatus.data[j].userId);
        }
        var index = layer.load(2, {time: 10 * 1000});
        $.ajax({
            url: '/cuxUsers/delete',
            type: 'post',
            data: {id: ids},
            success: function (data) {
                if (data > 0) {
                    layer.msg('成功删除' + data + '个');
                    layer.close(index);
                    reload();
                }
                else {
                    layer.msg('删除失败');
                    layer.close(index);
                }
            }
        })
    } else {
        layer.msg('请勾选批量删除的数据');
    }

}

//初始化layui变量
layui.use(['table', 'layer', 'layedit', 'element', 'upload', 'form', 'laydate'], function () {
    table = layui.table;
    $ = layui.$;
    layer = layui.layer;
    upload = layui.upload;
    form = layui.form;
    layedit = layui.layedit;
    element = layui.element;
    laydate = layui.laydate;
    //渲染table
    mytable = table.render({
        url: 'cuxUsers/selectToTable',
        method: 'post', //如果无需自定义HTTP类型，可不加该参数
        elem: '#demo', //指定原始表格元素选择器（推荐id选择器）
        page: true,
        limit: 10,
        id: 'userId',//容器唯一ID，id值是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引
        cols: [
            [
                {
                    checkbox: true
                },
                {
                    field: 'userId',
                    align: 'center',
                    title: '用户ID',
                    width: "10%"
                },
                {
                    field: 'userName',
                    align: 'center',
                    title: '用户名称',
                    width: "10%"
                },
                {
                    field: 'password',
                    align: 'center',
                    title: '用户密码',
                    width: "10%"
                },
                {
                    field: 'sex',
                    align: 'center',
                    title: '性别',
                    width: "10%"
                },
                {
                    field: 'age',
                    align: 'center',
                    title: '年龄',
                    width: "10%"
                },
                {
                    field: 'phoneNumber',
                    align: 'center',
                    title: '电话',
                    width: "10%"
                },
                {
                    field: 'creationDate',
                    align: 'center',
                    title: '创建时间',
                    width: "10%"
                },
                {
                    field: 'lastUpdateDate',
                    align: 'center',
                    title: '更新时间',
                    width: "10%"
                },
                {
                    field: 'comments',
                    align: 'center',
                    title: '备注',
                    width: "10%"
                },
                {
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#barDemo',
                    width: "15%"
                }
            ]
        ]
    });

    //监听工具条
    table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if (layEvent === 'detail') {//查看
            var html = document.getElementById('seeDOM_template').innerHTML;
            layer.open({
                type: 1,
                title: '查看页面',
                content: html,
                area: ['450px', '500px'],
                maxmin: true,
                success: function (layero, index) {
                    //日期
                    laydate.render({
                        elem:'#creationDate_see'
                    });
                    laydate.render({
                        elem:'#lastUpdateDate_see'
                    });
                }
            });
            var temp = 1;
            timer = setInterval(function (e) {
                if (temp < 100) {
                    temp += temp;
                    element.progress('progressdemo', temp + '%');
                } else {
                    element.progress('progressdemo', 100 + '%');
                    clearInterval(timer);
                }
            }, 100);
            //获取数据赋值给seeDOM
            $('input[name="userId_see"]').val(data.userId);
            $('input[name="userName_see"]').val(data.userName);
            $('input[name="sex_see"]').val(data.sex);
            $('input[name="password_see"]').val(data.password);
            $('input[name="phoneNumber_see"]').val(data.phoneNumber);
            $('input[name="creationDate_see"]').val(data.creationDate);
            $('input[name="lastUpdateDate_see"]').val(data.lastUpdateDate);
            $('input[name="comments_see"]').val(data.comments);
            $('input[name="age_see"]').val(data.age);
        }
        else if (layEvent === 'edit') { //编辑
            var html = document.getElementById('editDOM_template').innerHTML;
            var index = layer.open({
                type: 1,
                title: '编辑页面',
                maxmin: true, //开启最大化最小化按钮
                area: ['450px', '500px'],
                content: html,
                success: function (layero, index) {
                    //日期
                    laydate.render({
                        elem:'#creationDate_edit'
                    });
                    laydate.render({
                        elem:'#lastUpdateDate_edit'
                    });
                },
                cancel: function (index, layero) {
                    reload();
                },
            });
            var temp = 1;
            timer = setInterval(function (e) {
                if (temp < 100) {
                    temp += temp;
                    element.progress('progressdemo', temp + '%');
                } else {
                    element.progress('progressdemo', 100 + '%');
                    clearInterval(timer);
                }
            }, 100);
            //获取数据赋值给editDOM
            $('input[name="userId_edit"]').val(data.userId);
            $('input[name="userName_edit"]').val(data.userName);
            $('input[name="sex_edit"]').val(data.sex);
            $('input[name="password_edit"]').val(data.password);
            $('input[name="phoneNumber_edit"]').val(data.phoneNumber);
            $('input[name="creationDate_edit"]').val(data.creationDate);
            $('input[name="lastUpdateDate_edit"]').val(data.lastUpdateDate);
            $('input[name="comments_edit"]').val(data.comments);
            $('input[name="age_edit"]').val(data.age);
        }
        else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: 'cuxUsers/delete',
                    method: 'post',
                    data: {'id[]': data.userId},
                    success: function (data) {
                        if (data > 0) {
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.msg('删除成功');
                            layer.close(index);
                        }
                        else {
                            layer.msg('删除失败');
                            layer.close(index);
                        }
                    }
                })
            });
        }
    })

    //监听编辑按钮
    form.on('submit(submit_edit)', function (data) {
        var user = {
            userId: $('input[name="userId_edit"]').val(),
            userName: $('input[name="userName_edit"]').val(),
            password:$('input[name="password_edit"]').val(),
            sex: $('input[name="sex_edit"]').val(),
            phoneNumber: $('input[name="phoneNumber_edit"]').val(),
            creationDate: $('input[name="creationDate_edit"]').val(),
            lastUpdateDate: $('input[name="lastUpdateDate_edit"]').val(),
            comments: $('input[name="comments_edit"]').val(),
            age: $('input[name="age_edit"]').val(),
        }
        var index = layer.load(2, {time: 10 * 1000});
        $.ajax({
            url: 'cuxUsers/update',
            data: user,
            type: 'post',
            success: function (data) {
                if (data === "success") {
                    layer.msg('提交成功');
                    layer.close(index);
                }
                else {
                    layer.msg('提交失败');
                    layer.close(index);
                }
            }
        })
        return false;
    });

    //监听添加按钮
    form.on('submit(submit_add)', function (data) {
        var user = {
            userId: $('input[name="userId_add"]').val(),
            userName: $('input[name="userName_add"]').val(),
            password:$('input[name="password_add"]').val(),
            sex: $('input[name="sex_add"]').val(),
            phoneNumber: $('input[name="phoneNumber_add"]').val(),
            creationDate: $('input[name="creationDate_add"]').val(),
            lastUpdateDate: $('input[name="lastUpdateDate_add"]').val(),
            comments: $('input[name="comments_add"]').val(),
            age: $('input[name="age_add"]').val(),
        }
        var index = layer.load(2, {time: 10 * 1000});
        $.ajax({
            url: 'cuxUsers/insert',
            data: user,
            type: 'post',
            success: function (data) {
                if (data === "success") {
                    layer.msg('提交成功');
                    layer.close(index);
                }
                else {
                    layer.msg('提交失败');
                    layer.close(index);
                }
            }
        })
        return false;
    });

})
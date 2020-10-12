<%@page import="java.util.ArrayList"%>
    <%@ page language="java" import="vo.Download" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>下载列表</title>
                <style>
                    .d0 {
                        border: 1px solid yellowgreen;
                        text-align: left;
                        padding: 10px;
                    }
                </style>
           
            </head>

            <body>

                <strong>首页 |下载 | 设计 | 论坛 | 相册 | 关于  </strong>
                <table border="1" class="d0">
                    <c:forEach items="${list}" var="e">
                        <tr>
                            <th>
                                <img src="${e.image}" height="150" width="200" />
                            </th>
                            <th>
                                ${e.name}<br/> ${e.description}<br/>大小:${e.size}B
                            </th>
                            <th>
                                <form action="servlet/download.do" method="GET">
                                    <input type="text" name="path" value="${e.path}" id="path" />
                                    <button type="submit" style="background-color: chartreuse;">点击下载</button>
                                </form>


                            </th>

                        </tr>


                    </c:forEach>
                </table>
            </body>

            </html>
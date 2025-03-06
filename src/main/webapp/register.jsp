<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e7f3f9;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 35%;
            margin: 80px auto;
            padding: 25px;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%;
            padding: 12px;
            margin: 12px 0;
            border: 1px solid #bdc3c7;
            border-radius: 8px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 12px;
            border: none;
            background: #3498db;
            color: white;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background: #2980b9;
        }
        .message {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
        }
        .message a {
            color: #3498db;
            text-decoration: none;
        }
        .message a:hover {
            text-decoration: underline;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Register</h2>
    <form action="RegisterServlet" method="post">
        <input type="text" name="employee_id" placeholder="Employee ID" required>
        <input type="text" name="full_name" placeholder="Full Name" required>
        <input type="text" name="department" placeholder="Department" required>
        <input type="text" name="role" placeholder="Role" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="text" name="phone" placeholder="Phone" required>
        <input type="email" name="email" placeholder="Email" required>
        <button type="submit">Register</button>
    </form>
    <div class="message">
        Already have an account? <a href="login.jsp">Login</a>
    </div>
    <div class="error">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
    </div>
</div>
</body>
</html>
import { useState } from 'react';
import { login } from '../services/api';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const Login = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();



    const handleLogin = async () => {
        try {
            const res = await login(userName, password);
            localStorage.setItem('userId', res.id);
            navigate('/notes');
        } catch {
            alert('Login failed');
        }
    };

    const handleSignUp = () => {
        navigate('/signup');
    }

    return (
        <div className="container">
            <h2>Login</h2>
            <input
                type="text"
                placeholder="Username"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                className="input"
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="input"
            />
            <button onClick={handleLogin} className="button">
                Login
            </button>
            <button onClick={handleSignUp} className="button">
                Sign Up
            </button>
        </div>
    );
};

export default Login;

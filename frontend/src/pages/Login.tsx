import {useState} from 'react';
import {login} from '../services/api';
import {useNavigate} from 'react-router-dom';
import '../App.css';

const Login = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const [error, setError] = useState('');


    const handleLogin = async () => {
        if (!userName.trim() || !password.trim()) {
            setError("Please enter both username and password");
            return;
        }
        setError('');
        try {
            const res = await login(userName.trim(), password.trim());
            localStorage.setItem('userId', res);
            navigate('/notes');
        } catch (err) {
            setError(err.message || "Login Failed");
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
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default Login;

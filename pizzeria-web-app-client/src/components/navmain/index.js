import React from "react";
import "./style.css";
import { Link } from "react-router-dom";

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <span className="navbar-logo">🍕</span>
                <span className="navbar-title">SliceNJoy</span>
            </div>
            <ul className="navbar-links">
                <li>
                    <Link to="/">Начало</Link>
                </li>
                <li>
                    <Link to="/menu">Меню </Link>
                </li>
                <li>
                    <Link to="/signin">Вход</Link>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;

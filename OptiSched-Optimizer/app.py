from fastapi import FastAPI

app = FastAPI(
    title="OptiSched Optimizer",
    description="Optimization service for OptiSched",
    version="1.0.0"
)


@app.get("/")
def home():
    return {
        "service": "OptiSched Optimizer",
        "status": "running"
    }


@app.get("/health")
def health():
    return {
        "status": "healthy"
    }